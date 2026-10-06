package com.example.danishspelling.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "practice_sessions",
    foreignKeys = [
        ForeignKey(
            entity = PracticeList::class,
            parentColumns = ["id"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("listId")]
)
data class PracticeSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val listId: Long,
    val startTime: Long = System.currentTimeMillis(),
    val endTime: Long? = null,
    val totalSentences: Int = 0,
    val completedSentences: Int = 0,
    val totalStarsEarned: Int = 0
)
