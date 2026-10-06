package com.example.danishspelling.data.repository

import com.example.danishspelling.data.local.dao.SentenceDao
import com.example.danishspelling.data.local.entities.Sentence
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SentenceRepository @Inject constructor(
    private val sentenceDao: SentenceDao
) {
    fun getSentencesByListId(listId: Long): Flow<List<Sentence>> =
        sentenceDao.getSentencesByListId(listId)

    suspend fun getSentenceById(sentenceId: Long): Sentence? =
        sentenceDao.getSentenceById(sentenceId)

    suspend fun insertSentence(sentence: Sentence): Long = sentenceDao.insert(sentence)

    suspend fun insertSentences(sentences: List<Sentence>) = sentenceDao.insertAll(sentences)

    suspend fun updateSentence(sentence: Sentence) = sentenceDao.update(sentence)

    suspend fun deleteSentence(sentence: Sentence) = sentenceDao.delete(sentence)

    suspend fun softDeleteSentence(sentenceId: Long) = sentenceDao.softDelete(sentenceId)

    suspend fun getSentenceCountForList(listId: Long): Int =
        sentenceDao.getSentenceCountForList(listId)

    suspend fun updateOrderIndex(sentenceId: Long, newIndex: Int) =
        sentenceDao.updateOrderIndex(sentenceId, newIndex)

    suspend fun updateOrderIndices(updates: List<Pair<Long, Int>>) =
        sentenceDao.updateOrderIndices(updates)

    suspend fun incrementIncorrectCount(sentenceId: Long) =
        sentenceDao.incrementIncorrectCount(sentenceId)

    suspend fun resetIncorrectCount(sentenceId: Long) =
        sentenceDao.resetIncorrectCount(sentenceId)

    fun getIncorrectSentencesByListId(listId: Long): Flow<List<Sentence>> =
        sentenceDao.getIncorrectSentencesByListId(listId)

    suspend fun getIncorrectSentenceCountForList(listId: Long): Int =
        sentenceDao.getIncorrectSentenceCountForList(listId)
}
