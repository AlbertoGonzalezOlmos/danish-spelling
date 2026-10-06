package com.example.danishspelling.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.danishspelling.domain.model.DifficultyLevel

@Entity(tableName = "practice_lists")
data class PracticeList(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val difficultyLevel: DifficultyLevel,
    val colorTheme: String, // Hex color code
    val iconName: String, // Icon identifier
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
)
