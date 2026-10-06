package com.example.danishspelling.service.tts

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.io.File as JavaFile
import com.example.danishspelling.util.Constants
import com.example.danishspelling.util.DanishTextUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import java.security.MessageDigest
import java.util.*

/**
 * Default TTS engine using Android's built-in TextToSpeech API.
 * Works with Google TTS, Samsung TTS, or any system-installed TTS engine.
 */
class AndroidTTSEngine(
    private val context: Context
) : TTSEngine {

    private var tts: TextToSpeech? = null
    private var currentSpeed: Float = Constants.DEFAULT_TTS_SPEED
    @Volatile private var initRetryCount = 0
    @Volatile private var isInitializing = false

    private val _isInitialized = MutableStateFlow(false)
    override val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    private val _isSpeaking = MutableStateFlow(false)
    override val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    override val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val pendingSynthesis = Collections.synchronizedMap(
        mutableMapOf<String, kotlin.coroutines.Continuation<Unit>>()
    )

    init {
        initializeTTS()
    }

    private fun initializeTTS() {
        if (isInitializing) return
        isInitializing = true

        tts?.shutdown()
        tts = TextToSpeech(context) { status ->
            isInitializing = false
            if (status == TextToSpeech.SUCCESS) {
                initRetryCount = 0
                val result = tts?.setLanguage(DanishTextUtils.getDanishLocale())
                when (result) {
                    TextToSpeech.LANG_MISSING_DATA, TextToSpeech.LANG_NOT_SUPPORTED -> {
                        val fallbackResult = tts?.setLanguage(Locale("da"))
                        if (fallbackResult == TextToSpeech.LANG_MISSING_DATA ||
                            fallbackResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                            _errorMessage.value =
                                "Danish text-to-speech is not available. Please install Danish language support."
                            _isInitialized.value = false
                        } else {
                            onEngineReady()
                        }
                    }
                    else -> onEngineReady()
                }
            } else {
                _isInitialized.value = false
                if (initRetryCount < MAX_INIT_RETRIES) {
                    initRetryCount++
                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        initializeTTS()
                    }, RETRY_DELAY_MS * initRetryCount)
                } else {
                    _errorMessage.value = "Failed to initialize text-to-speech. " +
                            "Please check that a TTS engine is installed."
                }
            }
        }
    }

    private fun onEngineReady() {
        tts?.setSpeechRate(currentSpeed)
        _isInitialized.value = true
        _errorMessage.value = null
        setupProgressListener()
    }

    override fun ensureInitialized() {
        if (!_isInitialized.value && !isInitializing) {
            initRetryCount = 0
            initializeTTS()
        }
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

    override fun speak(text: String, speed: Float) {
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

    override fun stop() {
        tts?.stop()
        _isSpeaking.value = false
    }

    override fun setSpeed(speed: Float) {
        val clampedSpeed = speed.coerceIn(Constants.MIN_TTS_SPEED, Constants.MAX_TTS_SPEED)
        currentSpeed = clampedSpeed
        tts?.setSpeechRate(clampedSpeed)
    }

    override fun getCurrentSpeed(): Float = currentSpeed

    override fun isLocaleAvailable(): Boolean {
        val result = tts?.isLanguageAvailable(DanishTextUtils.getDanishLocale())
        return result == TextToSpeech.LANG_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_AVAILABLE
    }

    override suspend fun preGenerateClips(sentences: List<String>, outputDir: File) {
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

            val result = tts?.synthesizeToFile(text, Bundle.EMPTY, JavaFile(filePath), utteranceId)
            if (result != TextToSpeech.SUCCESS) {
                pendingSynthesis.remove(utteranceId)
                if (continuation.isActive) {
                    continuation.resumeWith(Result.success(Unit))
                }
            }
        }
    }

    override fun getAudioFile(text: String, audioDir: File): File? {
        val filename = getAudioFilename(text)
        val file = File(audioDir, filename)
        return if (file.exists()) file else null
    }

    private fun getAudioFilename(text: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(text.toByteArray(Charsets.UTF_8))
            .take(8)
            .joinToString("") { "%02x".format(it) }
        return "tts_$hash.wav"
    }

    override fun shutdown() {
        stop()
        tts?.shutdown()
        tts = null
        _isInitialized.value = false
    }

    companion object {
        private const val MAX_INIT_RETRIES = 3
        private const val RETRY_DELAY_MS = 1000L
        private const val UTTERANCE_SPEAK = "speak"
        private const val UTTERANCE_SYNTH_PREFIX = "synth_"
    }
}
