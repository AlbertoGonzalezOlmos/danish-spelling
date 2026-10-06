package com.example.danishspelling.data.repository

import com.example.danishspelling.data.local.dao.UserSettingsDao
import com.example.danishspelling.data.local.entities.UserSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSettingsRepository @Inject constructor(
    private val userSettingsDao: UserSettingsDao
) {
    fun getSettings(): Flow<UserSettings?> = userSettingsDao.getSettings()

    suspend fun getSettingsOnce(): UserSettings? = userSettingsDao.getSettingsOnce()

    suspend fun insertSettings(settings: UserSettings) = userSettingsDao.insert(settings)

    suspend fun updateSettings(settings: UserSettings) = userSettingsDao.update(settings)

    suspend fun addStars(stars: Int) = userSettingsDao.addStars(stars)

    suspend fun updateStreak(streak: Int, date: Long) =
        userSettingsDao.updateStreak(streak, date)

    suspend fun updateTtsSpeed(speed: Float) = userSettingsDao.updateTtsSpeed(speed)

    suspend fun getOrCreateSettings(): UserSettings {
        return getSettingsOnce() ?: UserSettings().also { insertSettings(it) }
    }
}
