package com.example.danishspelling.service

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.danishspelling.util.Constants
import com.example.danishspelling.util.DanishTextUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import java.security.MessageDigest
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DanishTTSService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var tts: TextToSpeech? = null
    private var currentSpeed: Float = Constants.DEFAULT_TTS_SPEED
    @Volatile private var isInitializing = false

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val pendingSynthesis = Collections.synchronizedMap(
        mutableMapOf<String, kotlin.coroutines.Continuation<Unit>>()
    )

    private var engineQueue: MutableList<String> = mutableListOf()

    init {
        startInitialization()
    }

    /**
     * Discover all TTS engine packages via PackageManager and try them in order.
     */
    private fun startInitialization() {
        if (isInitializing) return
        isInitializing = true

        // Query PackageManager for all apps that declare a TTS_SERVICE intent-filter.
        // This works even when no TTS instance exists yet.
        val ttsIntent = Intent(TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE)
        val resolvedEngines = context.packageManager
            .queryIntentServices(ttsIntent, PackageManager.MATCH_ALL)
            .map { it.serviceInfo.packageName }
            .distinct()

        Log.d(TAG, "TTS engines found via PackageManager: $resolvedEngines")

        // Always try the system default engine first (empty string = default constructor).
        // This works even when PackageManager returns nothing due to missing <queries>.
        engineQueue = mutableListOf("")

        if (resolvedEngines.isEmpty()) {
            Log.w(TAG, "PackageManager found no TTS engines; will try default constructor anyway")
        } else {
            resolvedEngines.forEach { pkg ->
                if (!engineQueue.contains(pkg)) engineQueue.add(pkg)
            }
        }

        tryNextEngine()
    }

    private fun tryNextEngine() {
        if (engineQueue.isEmpty()) {
            isInitializing = false
            _isInitialized.value = false
            _errorMessage.value = NO_ENGINE_MESSAGE
            Log.e(TAG, "All TTS engines failed to initialize")
            return
        }

        val enginePackage = engineQueue.removeFirst()
        val label = if (enginePackage.isEmpty()) "system-default" else enginePackage
        Log.d(TAG, "Trying TTS engine: $label")

        tts?.shutdown()
        tts = null

        val listener = TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                isInitializing = false
                Log.d(TAG, "TTS engine initialized: $label")
                onEngineConnected()
            } else {
                Log.w(TAG, "TTS engine failed: $label (status=$status)")
                tts?.shutdown()
                tts = null
                tryNextEngine()
            }
        }

        tts = if (enginePackage.isEmpty()) {
            TextToSpeech(context, listener)
        } else {
            TextToSpeech(context, listener, enginePackage)
        }
    }

    private fun onEngineConnected() {
        val result = tts?.setLanguage(DanishTextUtils.getDanishLocale())
        when (result) {
            TextToSpeech.LANG_MISSING_DATA, TextToSpeech.LANG_NOT_SUPPORTED -> {
                val fallback = tts?.setLanguage(Locale("da"))
                if (fallback == TextToSpeech.LANG_MISSING_DATA ||
                    fallback == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    Log.w(TAG, "Danish not supported by this engine, using default language")
                }
            }
        }

        tts?.setSpeechRate(currentSpeed)
        _isInitialized.value = true
        _errorMessage.value = null
        setupProgressListener()
    }

    fun ensureInitialized() {
        if (!_isInitialized.value && !isInitializing) {
            startInitialization()
        }
    }

    /**
     * Force a full re-initialization: shut down any existing instance and start fresh.
     * Use this after the user changes TTS settings externally.
     */
    fun reinitialize() {
        isInitializing = false
        tts?.shutdown()
        tts = null
        _isInitialized.value = false
        _errorMessage.value = null
        startInitialization()
    }

    /**
     * Returns an Intent that opens the system TTS settings screen,
     * where the user can install or enable a TTS engine.
     */
    fun getTTSSettingsIntent(): Intent {
        return Intent("com.android.settings.TTS_SETTINGS")
    }

    companion object {
        private const val TAG = "DanishTTS"
        private const val UTTERANCE_SPEAK = "speak"
        private const val UTTERANCE_SYNTH_PREFIX = "synth_"
        private const val NO_ENGINE_MESSAGE =
            "No text-to-speech engine available. Please open Settings → Accessibility → " +
            "Text-to-speech and install or enable a TTS engine."
    }

    private fun setupProgressListener() {
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                if (utteranceId == UTTERANCE_SPEAK) {
                    _isSpeaking.value = true
                }
            }

            override fun onDone(utteranceId: String?) {
                if (utteranceId == UTTERANCE_SPEAK) {
                    _isSpeaking.value = false
                } else if (utteranceId != null) {
                    pendingSynthesis.remove(utteranceId)?.resumeWith(Result.success(Unit))
                }
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                if (utteranceId == UTTERANCE_SPEAK) {
                    _isSpeaking.value = false
                    _errorMessage.value = "Error speaking text"
                } else if (utteranceId != null) {
                    pendingSynthesis.remove(utteranceId)?.resumeWith(Result.success(Unit))
                }
            }

            override fun onError(utteranceId: String?, errorCode: Int) {
                if (utteranceId == UTTERANCE_SPEAK) {
                    _isSpeaking.value = false
                    _errorMessage.value = "Error speaking text (code: $errorCode)"
                } else if (utteranceId != null) {
                    pendingSynthesis.remove(utteranceId)?.resumeWith(Result.success(Unit))
                }
            }
        })
    }

    fun speak(text: String, speed: Float = currentSpeed) {
        if (!_isInitialized.value) {
            ensureInitialized()
            _errorMessage.value = "Text-to-speech is initializing, please try again"
            return
        }

        tts?.let {
            it.setSpeechRate(speed)
            currentSpeed = speed
            it.speak(text, TextToSpeech.QUEUE_FLUSH, null, UTTERANCE_SPEAK)
        }
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
    }

    fun setSpeed(speed: Float) {
        val clampedSpeed = speed.coerceIn(Constants.MIN_TTS_SPEED, Constants.MAX_TTS_SPEED)
        currentSpeed = clampedSpeed
        tts?.setSpeechRate(clampedSpeed)
    }

    fun isLocaleAvailable(): Boolean {
        val result = tts?.isLanguageAvailable(DanishTextUtils.getDanishLocale())
        return result == TextToSpeech.LANG_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_AVAILABLE
    }

    fun getCurrentSpeed(): Float = currentSpeed

    suspend fun preGenerateClips(sentences: List<String>, outputDir: File) {
        if (!_isInitialized.value) {
            _errorMessage.value = "Text-to-speech is not initialized"
            return
        }

        withContext(Dispatchers.IO) {
            sentences.forEach { text ->
                val filename = getAudioFilename(text)
                val audioFile = File(outputDir, filename)
                if (!audioFile.exists()) {
                    synthesizeToFile(text, audioFile.absolutePath)
                }
            }
        }
    }

    private suspend fun synthesizeToFile(text: String, filePath: String) {
        return suspendCancellableCoroutine { continuation ->
            val utteranceId = "$UTTERANCE_SYNTH_PREFIX$filePath"
            pendingSynthesis[utteranceId] = continuation

            continuation.invokeOnCancellation {
                pendingSynthesis.remove(utteranceId)
            }

            val result = tts?.synthesizeToFile(text, Bundle.EMPTY, File(filePath), utteranceId)
            if (result != TextToSpeech.SUCCESS) {
                pendingSynthesis.remove(utteranceId)
                if (continuation.isActive) {
                    continuation.resumeWith(Result.success(Unit))
                }
            }
        }
    }

    private fun getAudioFilename(text: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(text.toByteArray(Charsets.UTF_8))
            .take(8)
            .joinToString("") { "%02x".format(it) }
        return "tts_$hash.wav"
    }

    fun getAudioFile(text: String, audioDir: File): File? {
        val filename = getAudioFilename(text)
        val file = File(audioDir, filename)
        return if (file.exists()) file else null
    }

    fun shutdown() {
        stop()
        tts?.shutdown()
        tts = null
        _isInitialized.value = false
    }
}
