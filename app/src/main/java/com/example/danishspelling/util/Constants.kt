package com.example.danishspelling.util

object Constants {
    // TTS Settings
    const val DEFAULT_TTS_SPEED = 1.0f
    const val MIN_TTS_SPEED = 0.5f
    const val MAX_TTS_SPEED = 1.5f
    const val TTS_LANGUAGE_CODE = "da"
    const val TTS_COUNTRY_CODE = "DK"

    // Star Calculation Thresholds
    const val THREE_STAR_THRESHOLD = 95f
    const val TWO_STAR_THRESHOLD = 80f
    const val ONE_STAR_THRESHOLD = 60f

    // UI Constants
    const val MIN_TOUCH_TARGET_SIZE_DP = 48
    const val ANIMATION_DURATION_MS = 300L
    const val CELEBRATION_ANIMATION_DURATION_MS = 2000L

    // Practice Session
    const val MAX_ATTEMPTS_PER_SENTENCE = 3
    const val SKIP_PENALTY_STARS = 0

    // Preferences Keys
    const val PREF_CHILD_NAME = "child_name"
    const val PREF_TTS_SPEED = "tts_speed"
    const val PREF_SOUND_EFFECTS_ENABLED = "sound_effects_enabled"
    const val PREF_ANIMATIONS_ENABLED = "animations_enabled"
}
