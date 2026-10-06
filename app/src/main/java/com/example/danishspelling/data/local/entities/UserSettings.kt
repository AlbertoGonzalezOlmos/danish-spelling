package com.example.danishspelling.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey
    val id: Int = 1, // Single row
    val childName: String = "",
    val ttsSpeedMultiplier: Float = 1.0f, // 0.5 to 1.5
    val ttsLanguage: String = "da-DK", // Danish
    val soundEffectsEnabled: Boolean = true,
    val animationsEnabled: Boolean = true,
    val totalStarsEarned: Int = 0,
    val currentStreak: Int = 0, // Days practiced consecutively
    val lastPracticeDate: Long? = null
)
