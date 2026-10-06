package com.example.danishspelling.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sentences",
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
data class Sentence(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val listId: Long,
    val text: String,
    val difficulty: Int = 1, // 1-5 scale
    val hints: String? = null,
    val orderIndex: Int = 0,
    val isActive: Boolean = true,
    val incorrectCount: Int = 0, // Track how many times marked incorrect
    val createdAt: Long = System.currentTimeMillis()
)
