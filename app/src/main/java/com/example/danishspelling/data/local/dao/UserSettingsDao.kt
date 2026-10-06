package com.example.danishspelling.data.local.dao

import androidx.room.*
import com.example.danishspelling.data.local.entities.UserSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSettingsDao {
    @Query("SELECT * FROM user_settings WHERE id = 1")
    fun getSettings(): Flow<UserSettings?>

    @Query("SELECT * FROM user_settings WHERE id = 1")
    suspend fun getSettingsOnce(): UserSettings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: UserSettings)

    @Update
    suspend fun update(settings: UserSettings)

    @Query("UPDATE user_settings SET totalStarsEarned = totalStarsEarned + :stars WHERE id = 1")
    suspend fun addStars(stars: Int)

    @Query("UPDATE user_settings SET currentStreak = :streak, lastPracticeDate = :date WHERE id = 1")
    suspend fun updateStreak(streak: Int, date: Long)

    @Query("UPDATE user_settings SET ttsSpeedMultiplier = :speed WHERE id = 1")
    suspend fun updateTtsSpeed(speed: Float)
}
