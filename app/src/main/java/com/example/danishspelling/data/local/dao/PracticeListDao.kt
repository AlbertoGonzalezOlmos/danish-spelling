package com.example.danishspelling.data.local.dao

import androidx.room.*
import com.example.danishspelling.data.local.entities.PracticeList
import kotlinx.coroutines.flow.Flow

@Dao
interface PracticeListDao {
    @Query("SELECT * FROM practice_lists WHERE isActive = 1 ORDER BY createdAt DESC")
    fun getAllLists(): Flow<List<PracticeList>>

    @Query("SELECT * FROM practice_lists WHERE id = :listId")
    suspend fun getListById(listId: Long): PracticeList?

    @Query("SELECT * FROM practice_lists WHERE id = :listId")
    fun getListByIdFlow(listId: Long): Flow<PracticeList?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(practiceList: PracticeList): Long

    @Update
    suspend fun update(practiceList: PracticeList)

    @Delete
    suspend fun delete(practiceList: PracticeList)

    @Query("UPDATE practice_lists SET isActive = 0 WHERE id = :listId")
    suspend fun softDelete(listId: Long)

    @Query("SELECT COUNT(*) FROM practice_lists WHERE isActive = 1")
    suspend fun getActiveListCount(): Int
}
