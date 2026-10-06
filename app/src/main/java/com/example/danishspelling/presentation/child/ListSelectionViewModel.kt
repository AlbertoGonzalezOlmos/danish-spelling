package com.example.danishspelling.presentation.child

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danishspelling.data.local.entities.PracticeList
import com.example.danishspelling.data.repository.PracticeListRepository
import com.example.danishspelling.data.repository.SentenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListSelectionViewModel @Inject constructor(
    practiceListRepository: PracticeListRepository,
    private val sentenceRepository: SentenceRepository
) : ViewModel() {

    val practiceLists: StateFlow<List<PracticeList>> = practiceListRepository
        .getAllLists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    suspend fun getSentenceCounts(listId: Long): Pair<Int, Int> {
        val total = sentenceRepository.getSentenceCountForList(listId)
        val incorrect = sentenceRepository.getIncorrectSentenceCountForList(listId)
        return Pair(total, incorrect)
    }
}
