package com.example.danishspelling.data.local.dao

import androidx.room.*
import com.example.danishspelling.data.local.entities.Sentence
import kotlinx.coroutines.flow.Flow

@Dao
interface SentenceDao {
    @Query("SELECT * FROM sentences WHERE listId = :listId AND isActive = 1 ORDER BY orderIndex ASC")
    fun getSentencesByListId(listId: Long): Flow<List<Sentence>>

    @Query("SELECT * FROM sentences WHERE id = :sentenceId")
    suspend fun getSentenceById(sentenceId: Long): Sentence?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sentence: Sentence): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sentences: List<Sentence>)

    @Update
    suspend fun update(sentence: Sentence)

    @Delete
    suspend fun delete(sentence: Sentence)

    @Query("UPDATE sentences SET isActive = 0 WHERE id = :sentenceId")
    suspend fun softDelete(sentenceId: Long)

    @Query("SELECT COUNT(*) FROM sentences WHERE listId = :listId AND isActive = 1")
    suspend fun getSentenceCountForList(listId: Long): Int

    @Query("UPDATE sentences SET orderIndex = :newIndex WHERE id = :sentenceId")
    suspend fun updateOrderIndex(sentenceId: Long, newIndex: Int)

    @Transaction
    suspend fun updateOrderIndices(updates: List<Pair<Long, Int>>) {
        for ((id, index) in updates) {
            updateOrderIndex(id, index)
        }
    }

    @Query("UPDATE sentences SET incorrectCount = incorrectCount + 1 WHERE id = :sentenceId")
    suspend fun incrementIncorrectCount(sentenceId: Long)

    @Query("UPDATE sentences SET incorrectCount = 0 WHERE id = :sentenceId")
    suspend fun resetIncorrectCount(sentenceId: Long)

    @Query("SELECT * FROM sentences WHERE listId = :listId AND isActive = 1 AND incorrectCount > 0 ORDER BY incorrectCount DESC, orderIndex ASC")
    fun getIncorrectSentencesByListId(listId: Long): Flow<List<Sentence>>

    @Query("SELECT COUNT(*) FROM sentences WHERE listId = :listId AND isActive = 1 AND incorrectCount > 0")
    suspend fun getIncorrectSentenceCountForList(listId: Long): Int
}
