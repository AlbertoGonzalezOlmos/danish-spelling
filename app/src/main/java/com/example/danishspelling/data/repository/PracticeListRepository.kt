package com.example.danishspelling.data.repository

import com.example.danishspelling.data.local.dao.PracticeListDao
import com.example.danishspelling.data.local.entities.PracticeList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PracticeListRepository @Inject constructor(
    private val practiceListDao: PracticeListDao
) {
    fun getAllLists(): Flow<List<PracticeList>> = practiceListDao.getAllLists()

    fun getListById(listId: Long): Flow<PracticeList?> = practiceListDao.getListByIdFlow(listId)

    suspend fun getListByIdOnce(listId: Long): PracticeList? = practiceListDao.getListById(listId)

    suspend fun insertList(practiceList: PracticeList): Long = practiceListDao.insert(practiceList)

    suspend fun updateList(practiceList: PracticeList) = practiceListDao.update(practiceList)

    suspend fun deleteList(practiceList: PracticeList) = practiceListDao.delete(practiceList)

    suspend fun softDeleteList(listId: Long) = practiceListDao.softDelete(listId)

    suspend fun getActiveListCount(): Int = practiceListDao.getActiveListCount()
}
