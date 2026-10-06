package com.example.danishspelling.presentation.parent

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danishspelling.data.local.entities.Sentence
import com.example.danishspelling.data.repository.SentenceRepository
import com.example.danishspelling.service.DanishTTSService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SentenceManagementViewModel @Inject constructor(
    private val sentenceRepository: SentenceRepository,
    private val ttsService: DanishTTSService,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _sentences = MutableStateFlow<List<Sentence>>(emptyList())
    val sentences: StateFlow<List<Sentence>> = _sentences.asStateFlow()

    private var currentListId: Long = 0

    fun loadSentences(listId: Long) {
        currentListId = listId
        viewModelScope.launch {
            sentenceRepository.getSentencesByListId(listId).collect { sentenceList ->
                _sentences.value = sentenceList
            }
        }
    }

    fun addSentence(listId: Long, text: String) {
        viewModelScope.launch {
            val currentCount = sentenceRepository.getSentenceCountForList(listId)

            val newSentence = Sentence(
                listId = listId,
                text = text,
                difficulty = 2, // Default difficulty
                orderIndex = currentCount
            )

            sentenceRepository.insertSentence(newSentence)
        }
    }

    fun addSentences(listId: Long, texts: List<String>) {
        viewModelScope.launch {
            val currentCount = sentenceRepository.getSentenceCountForList(listId)

            val newSentences = texts.mapIndexed { index, text ->
                Sentence(
                    listId = listId,
                    text = text,
                    difficulty = 2,
                    orderIndex = currentCount + index
                )
            }

            sentenceRepository.insertSentences(newSentences)
        }
    }

    fun updateSentence(sentenceId: Long, newText: String) {
        viewModelScope.launch {
            val sentence = sentenceRepository.getSentenceById(sentenceId)
            sentence?.let {
                val updatedSentence = it.copy(text = newText)
                sentenceRepository.updateSentence(updatedSentence)
            }
        }
    }

    fun deleteSentence(sentenceId: Long) {
        viewModelScope.launch {
            val sentence = sentenceRepository.getSentenceById(sentenceId)
            sentence?.let {
                sentenceRepository.deleteSentence(it)
            }
        }
    }

    fun updateSentenceOrder(sentenceId: Long, newIndex: Int) {
        viewModelScope.launch {
            sentenceRepository.updateOrderIndex(sentenceId, newIndex)
        }
    }

    fun updateSentenceOrders(reorderedList: List<Sentence>) {
        viewModelScope.launch {
            val updates = reorderedList.mapIndexed { index, sentence ->
                Pair(sentence.id, index)
            }
            sentenceRepository.updateOrderIndices(updates)
        }
    }

    fun generateTTSClipsAsync() {
        viewModelScope.launch {
            val sentenceTexts = _sentences.value.map { it.text }
            if (sentenceTexts.isNotEmpty()) {
                val audioDir = File(context.filesDir, "tts_clips")
                if (!audioDir.exists()) {
                    audioDir.mkdirs()
                }
                ttsService.preGenerateClips(sentenceTexts, audioDir)
            }
        }
    }
}
