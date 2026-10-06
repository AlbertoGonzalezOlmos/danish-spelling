package com.example.danishspelling.data.local.dao

import androidx.room.*
import com.example.danishspelling.data.local.entities.PracticeAttempt
import kotlinx.coroutines.flow.Flow

@Dao
interface PracticeAttemptDao {
    @Query("SELECT * FROM practice_attempts WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    fun getAttemptsBySessionId(sessionId: Long): Flow<List<PracticeAttempt>>

    @Query("SELECT * FROM practice_attempts WHERE sentenceId = :sentenceId ORDER BY timestamp DESC")
    fun getAttemptsBySentenceId(sentenceId: Long): Flow<List<PracticeAttempt>>

    @Query("SELECT * FROM practice_attempts WHERE id = :attemptId")
    suspend fun getAttemptById(attemptId: Long): PracticeAttempt?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attempt: PracticeAttempt): Long

    @Update
    suspend fun update(attempt: PracticeAttempt)

    @Delete
    suspend fun delete(attempt: PracticeAttempt)

    @Query("SELECT AVG(accuracy) FROM practice_attempts WHERE sentenceId = :sentenceId")
    suspend fun getAverageAccuracyForSentence(sentenceId: Long): Float?

    @Query("SELECT COUNT(*) FROM practice_attempts WHERE sentenceId = :sentenceId")
    suspend fun getAttemptCountForSentence(sentenceId: Long): Int

    @Query("SELECT AVG(accuracy) FROM practice_attempts WHERE sessionId = :sessionId")
    suspend fun getAverageAccuracyForSession(sessionId: Long): Float?
}
