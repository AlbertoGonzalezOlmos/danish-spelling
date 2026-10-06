package com.example.danishspelling.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "practice_attempts",
    foreignKeys = [
        ForeignKey(
            entity = Sentence::class,
            parentColumns = ["id"],
            childColumns = ["sentenceId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PracticeSession::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("sentenceId"), Index("sessionId")]
)
data class PracticeAttempt(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sentenceId: Long,
    val sessionId: Long,
    val userInput: String,
    val correctText: String,
    val accuracy: Float, // 0.0 to 100.0
    val starsEarned: Int, // 0-3 stars
    val attemptNumber: Int, // Number of attempts for this sentence in this session
    val timestamp: Long = System.currentTimeMillis(),
    val speechSpeedUsed: Float = 1.0f // TTS speed setting used
)
