package com.example.danishspelling.presentation.parent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danishspelling.data.local.entities.PracticeList
import com.example.danishspelling.data.repository.PracticeListRepository
import com.example.danishspelling.domain.model.DifficultyLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListManagementViewModel @Inject constructor(
    private val practiceListRepository: PracticeListRepository
) : ViewModel() {

    val practiceLists: StateFlow<List<PracticeList>> = practiceListRepository
        .getAllLists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addList(name: String, description: String, difficulty: DifficultyLevel) {
        viewModelScope.launch {
            val newList = PracticeList(
                name = name,
                description = description,
                difficultyLevel = difficulty,
                colorTheme = getColorForDifficulty(difficulty),
                iconName = "default"
            )
            practiceListRepository.insertList(newList)
        }
    }

    fun deleteList(listId: Long) {
        viewModelScope.launch {
            val list = practiceListRepository.getListByIdOnce(listId)
            list?.let { practiceListRepository.deleteList(it) }
        }
    }

    private fun getColorForDifficulty(difficulty: DifficultyLevel): String {
        return when (difficulty) {
            DifficultyLevel.EASY -> "#4CAF50"    // Green
            DifficultyLevel.MEDIUM -> "#FF9800"  // Orange
            DifficultyLevel.HARD -> "#F44336"    // Red
        }
    }
}
