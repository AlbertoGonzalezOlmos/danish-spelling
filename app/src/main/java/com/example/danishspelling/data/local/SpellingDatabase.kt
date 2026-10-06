package com.example.danishspelling.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.danishspelling.data.local.dao.*
import com.example.danishspelling.data.local.entities.*

@Database(
    entities = [
        PracticeList::class,
        Sentence::class,
        PracticeSession::class,
        PracticeAttempt::class,
        UserSettings::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SpellingDatabase : RoomDatabase() {
    abstract fun practiceListDao(): PracticeListDao
    abstract fun sentenceDao(): SentenceDao
    abstract fun practiceSessionDao(): PracticeSessionDao
    abstract fun practiceAttemptDao(): PracticeAttemptDao
    abstract fun userSettingsDao(): UserSettingsDao

    companion object {
        const val DATABASE_NAME = "spelling_database"
    }
}
