package com.example.danishspelling.data.local

import androidx.room.TypeConverter
import com.example.danishspelling.domain.model.DifficultyLevel

class Converters {
    @TypeConverter
    fun fromDifficultyLevel(value: DifficultyLevel): String {
        return value.name
    }

    @TypeConverter
    fun toDifficultyLevel(value: String): DifficultyLevel {
        return DifficultyLevel.valueOf(value)
    }
}
