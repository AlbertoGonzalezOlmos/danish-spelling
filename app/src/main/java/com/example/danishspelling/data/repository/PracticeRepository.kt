package com.example.danishspelling.data.repository

import com.example.danishspelling.data.local.dao.PracticeAttemptDao
import com.example.danishspelling.data.local.dao.PracticeSessionDao
import com.example.danishspelling.data.local.entities.PracticeAttempt
import com.example.danishspelling.data.local.entities.PracticeSession
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PracticeRepository @Inject constructor(
    private val sessionDao: PracticeSessionDao,
    private val attemptDao: PracticeAttemptDao
) {
    // Session operations
    fun getSessionsByListId(listId: Long): Flow<List<PracticeSession>> =
        sessionDao.getSessionsByListId(listId)

    suspend fun getSessionById(sessionId: Long): PracticeSession? =
        sessionDao.getSessionById(sessionId)

    fun getRecentSessions(): Flow<List<PracticeSession>> = sessionDao.getRecentSessions()

    suspend fun insertSession(session: PracticeSession): Long = sessionDao.insert(session)

    suspend fun updateSession(session: PracticeSession) = sessionDao.update(session)

    suspend fun getSessionCountForList(listId: Long): Int =
        sessionDao.getSessionCountForList(listId)

    suspend fun getTotalStarsForList(listId: Long): Int =
        sessionDao.getTotalStarsForList(listId) ?: 0

    // Attempt operations
    fun getAttemptsBySessionId(sessionId: Long): Flow<List<PracticeAttempt>> =
        attemptDao.getAttemptsBySessionId(sessionId)

    fun getAttemptsBySentenceId(sentenceId: Long): Flow<List<PracticeAttempt>> =
        attemptDao.getAttemptsBySentenceId(sentenceId)

    suspend fun insertAttempt(attempt: PracticeAttempt): Long = attemptDao.insert(attempt)

    suspend fun getAverageAccuracyForSentence(sentenceId: Long): Float =
        attemptDao.getAverageAccuracyForSentence(sentenceId) ?: 0f

    suspend fun getAttemptCountForSentence(sentenceId: Long): Int =
        attemptDao.getAttemptCountForSentence(sentenceId)

    suspend fun getAverageAccuracyForSession(sessionId: Long): Float =
        attemptDao.getAverageAccuracyForSession(sessionId) ?: 0f
}
