package com.example.danishspelling.service.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import com.example.danishspelling.util.Constants
import com.example.danishspelling.util.DanishTextUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.util.*

/**
 * TTS engine implementation for Xiaomi devices.
 *
 * Uses Android's TextToSpeech API but explicitly selects the Xiaomi TTS engine
 * package ("com.xiaomi.mibrain.speech") when available. Falls back to the default
 * system engine if the Xiaomi engine is not installed.
 *
 * Extend this class to integrate Xiaomi-specific TTS features or SDK calls
 * if deeper integration is needed beyond the standard Android TTS API.
 */
class XiaomiTTSEngine(
    private val context: Context
) : TTSEngine {

    private var delegate: AndroidTTSEngine? = null
    private var tts: TextToSpeech? = null

    private val _isInitialized = MutableStateFlow(false)
    override val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    private val _isSpeaking = MutableStateFlow(false)
    override val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    override val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        initializeWithXiaomiEngine()
    }

    private fun initializeWithXiaomiEngine() {
        // Try to use the Xiaomi TTS engine explicitly
        tts = TextToSpeech(context, { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(DanishTextUtils.getDanishLocale())
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Xiaomi engine doesn't support Danish — fall back to default engine
                    tts?.shutdown()
                    tts = null
                    fallbackToDefault()
                } else {
                    tts?.setSpeechRate(Constants.DEFAULT_TTS_SPEED)
                    _isInitialized.value = true
                    _errorMessage.value = null
                }
            } else {
                // Xiaomi engine not available — fall back to default
                fallbackToDefault()
            }
        }, XIAOMI_TTS_PACKAGE)
    }

    private fun fallbackToDefault() {
        delegate = AndroidTTSEngine(context)
    }

    private fun activeDelegate(): AndroidTTSEngine? = delegate

    override fun ensureInitialized() {
        activeDelegate()?.ensureInitialized()
            ?: run {
                if (!_isInitialized.value) {
                    initializeWithXiaomiEngine()
                }
            }
    }

    override fun speak(text: String, speed: Float) {
        activeDelegate()?.speak(text, speed)
            ?: run {
                tts?.setSpeechRate(speed)
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "speak")
            }
    }

    override fun stop() {
        activeDelegate()?.stop() ?: tts?.stop()
        _isSpeaking.value = false
    }

    override fun setSpeed(speed: Float) {
        val clamped = speed.coerceIn(Constants.MIN_TTS_SPEED, Constants.MAX_TTS_SPEED)
        activeDelegate()?.setSpeed(clamped) ?: tts?.setSpeechRate(clamped)
    }

    override fun getCurrentSpeed(): Float =
        activeDelegate()?.getCurrentSpeed() ?: Constants.DEFAULT_TTS_SPEED

    override fun isLocaleAvailable(): Boolean {
        if (activeDelegate() != null) return activeDelegate()!!.isLocaleAvailable()
        val result = tts?.isLanguageAvailable(DanishTextUtils.getDanishLocale())
        return result == TextToSpeech.LANG_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_AVAILABLE
    }

    override suspend fun preGenerateClips(sentences: List<String>, outputDir: File) {
        activeDelegate()?.preGenerateClips(sentences, outputDir)
    }

    override fun getAudioFile(text: String, audioDir: File): File? {
        return activeDelegate()?.getAudioFile(text, audioDir)
    }

    override fun shutdown() {
        activeDelegate()?.shutdown()
        tts?.stop()
        tts?.shutdown()
        tts = null
        _isInitialized.value = false
    }

    companion object {
        const val XIAOMI_TTS_PACKAGE = "com.xiaomi.mibrain.speech"
    }
}
