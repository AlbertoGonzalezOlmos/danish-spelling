package com.example.danishspelling.data.local.dao

import androidx.room.*
import com.example.danishspelling.data.local.entities.PracticeSession
import kotlinx.coroutines.flow.Flow

@Dao
interface PracticeSessionDao {
    @Query("SELECT * FROM practice_sessions WHERE listId = :listId ORDER BY startTime DESC")
    fun getSessionsByListId(listId: Long): Flow<List<PracticeSession>>

    @Query("SELECT * FROM practice_sessions WHERE id = :sessionId")
    suspend fun getSessionById(sessionId: Long): PracticeSession?

    @Query("SELECT * FROM practice_sessions ORDER BY startTime DESC LIMIT 10")
    fun getRecentSessions(): Flow<List<PracticeSession>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: PracticeSession): Long

    @Update
    suspend fun update(session: PracticeSession)

    @Delete
    suspend fun delete(session: PracticeSession)

    @Query("SELECT COUNT(*) FROM practice_sessions WHERE listId = :listId")
    suspend fun getSessionCountForList(listId: Long): Int

    @Query("SELECT SUM(totalStarsEarned) FROM practice_sessions WHERE listId = :listId")
    suspend fun getTotalStarsForList(listId: Long): Int?
}
