package com.example.danishspelling.service.tts

import kotlinx.coroutines.flow.StateFlow
import java.io.File

/**
 * Abstraction for text-to-speech engines.
 * Implementations can use Google TTS, Xiaomi TTS, or any other engine.
 */
interface TTSEngine {
    val isInitialized: StateFlow<Boolean>
    val isSpeaking: StateFlow<Boolean>
    val errorMessage: StateFlow<String?>

    fun ensureInitialized()
    fun speak(text: String, speed: Float)
    fun stop()
    fun setSpeed(speed: Float)
    fun getCurrentSpeed(): Float
    fun isLocaleAvailable(): Boolean
    suspend fun preGenerateClips(sentences: List<String>, outputDir: File)
    fun getAudioFile(text: String, audioDir: File): File?
    fun shutdown()
}
