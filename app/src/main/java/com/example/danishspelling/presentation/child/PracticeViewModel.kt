package com.example.danishspelling.presentation.child

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danishspelling.data.local.entities.PracticeAttempt
import com.example.danishspelling.data.local.entities.PracticeSession
import com.example.danishspelling.data.local.entities.Sentence
import com.example.danishspelling.data.repository.PracticeRepository
import com.example.danishspelling.data.repository.SentenceRepository
import com.example.danishspelling.data.repository.UserSettingsRepository
import com.example.danishspelling.service.DanishTTSService
import com.example.danishspelling.service.Reward
import com.example.danishspelling.service.RewardSystem
import com.example.danishspelling.service.SpellingAccuracyCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val sentenceRepository: SentenceRepository,
    private val practiceRepository: PracticeRepository,
    private val userSettingsRepository: UserSettingsRepository,
    private val ttsService: DanishTTSService,
    private val spellingCalculator: SpellingAccuracyCalculator,
    private val rewardSystem: RewardSystem
) : ViewModel() {

    private val _practiceState = MutableStateFlow(PracticeState())
    val practiceState: StateFlow<PracticeState> = _practiceState.asStateFlow()

    private val _ttsState = MutableStateFlow(TTSState())
    val ttsState: StateFlow<TTSState> = _ttsState.asStateFlow()

    private var currentSessionId: Long = 0
    private var sentences: List<Sentence> = emptyList()
    private var currentSentenceAttempts = 0
    private var sessionStarted = false

    init {
        observeTTS()
    }

    private fun observeTTS() {
        viewModelScope.launch {
            ttsService.isSpeaking.collect { isSpeaking ->
                _ttsState.update { it.copy(isSpeaking = isSpeaking) }
            }
        }

        viewModelScope.launch {
            ttsService.errorMessage.collect { error ->
                _ttsState.update { it.copy(errorMessage = error) }
            }
        }
    }

    fun startPracticeSession(listId: Long, randomOrder: Boolean = false, incorrectOnly: Boolean = false) {
        if (sessionStarted) return
        sessionStarted = true
        viewModelScope.launch {
            _practiceState.update { it.copy(isLoading = true) }

            try {
                // Load sentences based on filter
                val allSentences = if (incorrectOnly) {
                    sentenceRepository.getIncorrectSentencesByListId(listId).first()
                } else {
                    sentenceRepository.getSentencesByListId(listId).first()
                }

                if (allSentences.isEmpty()) {
                    _practiceState.update {
                        it.copy(
                            isLoading = false,
                            error = if (incorrectOnly)
                                "No incorrect sentences to practice"
                            else
                                "No sentences in this list"
                        )
                    }
                    return@launch
                }

                // Apply random order if requested
                sentences = if (randomOrder) {
                    allSentences.shuffled()
                } else {
                    allSentences
                }

                // Create practice session
                val session = PracticeSession(
                    listId = listId,
                    totalSentences = sentences.size
                )
                currentSessionId = practiceRepository.insertSession(session)

                // Initialize first sentence
                _practiceState.update {
                    it.copy(
                        isLoading = false,
                        currentSentence = sentences[0],
                        currentIndex = 0,
                        totalSentences = sentences.size,
                        error = null
                    )
                }

                // Auto-play first sentence
                speakCurrentSentence()
            } catch (e: Exception) {
                _practiceState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load practice session: ${e.message}"
                    )
                }
            }
        }
    }

    fun speakCurrentSentence() {
        val sentence = _practiceState.value.currentSentence ?: return
        viewModelScope.launch {
            val settings = userSettingsRepository.getSettingsOnce()
            ttsService.speak(sentence.text, settings?.ttsSpeedMultiplier ?: 1.0f)
        }
    }

    fun checkAnswer(userInput: String) {
        val state = _practiceState.value
        val currentSentence = state.currentSentence ?: return

        viewModelScope.launch {
            currentSentenceAttempts++

            // Calculate accuracy
            val accuracy = spellingCalculator.calculateAccuracy(userInput, currentSentence.text)
            val stars = spellingCalculator.calculateStars(accuracy)
            val feedbackLevel = spellingCalculator.getFeedbackLevel(accuracy)

            // Create reward
            val reward = rewardSystem.calculateReward(accuracy, currentSentenceAttempts, stars)

            // Save attempt
            val attempt = PracticeAttempt(
                sentenceId = currentSentence.id,
                sessionId = currentSessionId,
                userInput = userInput,
                correctText = currentSentence.text,
                accuracy = accuracy,
                starsEarned = stars,
                attemptNumber = currentSentenceAttempts
            )
            practiceRepository.insertAttempt(attempt)

            // Update session stats
            val newStats = state.sessionStats.copy(
                totalStars = state.sessionStats.totalStars + stars,
                totalAttempts = state.sessionStats.totalAttempts + 1,
                averageAccuracy = calculateNewAverage(
                    state.sessionStats.averageAccuracy,
                    accuracy,
                    state.sessionStats.totalAttempts + 1
                )
            )

            // Update user settings with stars
            userSettingsRepository.addStars(stars)

            // Update state with feedback
            _practiceState.update {
                it.copy(
                    lastFeedback = reward,
                    sessionStats = newStats,
                    isLastSentence = state.currentIndex == sentences.size - 1
                )
            }
        }
    }

    fun moveToNextSentence() {
        val state = _practiceState.value
        val nextIndex = state.currentIndex + 1

        if (nextIndex >= sentences.size) {
            completeSession()
            return
        }

        currentSentenceAttempts = 0
        _practiceState.update {
            it.copy(
                currentSentence = sentences[nextIndex],
                currentIndex = nextIndex,
                lastFeedback = null,
                isLastSentence = nextIndex == sentences.size - 1
            )
        }

        // Auto-play next sentence
        speakCurrentSentence()
    }

    fun skipSentence() {
        // No stars earned for skipping
        moveToNextSentence()
    }

    private fun completeSession() {
        viewModelScope.launch {
            val state = _practiceState.value

            // Update session in database
            val session = practiceRepository.getSessionById(currentSessionId)
            session?.let {
                practiceRepository.updateSession(
                    it.copy(
                        endTime = System.currentTimeMillis(),
                        completedSentences = sentences.size,
                        totalStarsEarned = state.sessionStats.totalStars
                    )
                )
            }

            _practiceState.update {
                it.copy(isSessionComplete = true)
            }
        }
    }

    private fun calculateNewAverage(currentAvg: Float, newValue: Float, count: Int): Float {
        return ((currentAvg * (count - 1)) + newValue) / count
    }

    fun markAsCorrect() {
        viewModelScope.launch {
            val state = _practiceState.value
            val currentSentence = state.currentSentence ?: return@launch

            currentSentenceAttempts++

            // Record as correct (3 stars)
            val attempt = PracticeAttempt(
                sentenceId = currentSentence.id,
                sessionId = currentSessionId,
                userInput = "",
                correctText = currentSentence.text,
                accuracy = 100f,
                starsEarned = 3,
                attemptNumber = currentSentenceAttempts,
                timestamp = System.currentTimeMillis()
            )
            practiceRepository.insertAttempt(attempt)

            // Reset incorrect count if they got it correct
            sentenceRepository.resetIncorrectCount(currentSentence.id)

            // Update session stats
            val newStats = state.sessionStats.copy(
                totalStars = state.sessionStats.totalStars + 3,
                totalAttempts = state.sessionStats.totalAttempts + 1
            )

            _practiceState.update {
                it.copy(
                    sessionStats = newStats,
                    lastFeedback = rewardSystem.calculateReward(100f, currentSentenceAttempts, 3)
                )
            }
        }
    }

    fun markAsIncorrect() {
        viewModelScope.launch {
            val state = _practiceState.value
            val currentSentence = state.currentSentence ?: return@launch

            currentSentenceAttempts++

            // Record as incorrect (0 stars)
            val attempt = PracticeAttempt(
                sentenceId = currentSentence.id,
                sessionId = currentSessionId,
                userInput = "",
                correctText = currentSentence.text,
                accuracy = 0f,
                starsEarned = 0,
                attemptNumber = currentSentenceAttempts,
                timestamp = System.currentTimeMillis()
            )
            practiceRepository.insertAttempt(attempt)

            // Increment incorrect count
            sentenceRepository.incrementIncorrectCount(currentSentence.id)

            // Update session stats
            val newStats = state.sessionStats.copy(
                totalAttempts = state.sessionStats.totalAttempts + 1
            )

            _practiceState.update {
                it.copy(
                    sessionStats = newStats,
                    lastFeedback = rewardSystem.calculateReward(0f, currentSentenceAttempts, 0)
                )
            }
        }
    }

    fun moveToPreviousSentence() {
        val state = _practiceState.value
        if (state.currentIndex > 0) {
            val newIndex = state.currentIndex - 1
            currentSentenceAttempts = 0
            _practiceState.update {
                it.copy(
                    currentIndex = newIndex,
                    currentSentence = sentences[newIndex],
                    lastFeedback = null,
                    isLastSentence = newIndex == sentences.size - 1
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsService.stop()
    }
}

data class PracticeState(
    val isLoading: Boolean = false,
    val currentSentence: Sentence? = null,
    val currentIndex: Int = 0,
    val totalSentences: Int = 0,
    val lastFeedback: Reward? = null,
    val sessionStats: SessionStats = SessionStats(),
    val isSessionComplete: Boolean = false,
    val isLastSentence: Boolean = false,
    val error: String? = null
)

data class SessionStats(
    val totalStars: Int = 0,
    val totalAttempts: Int = 0,
    val averageAccuracy: Float = 0f
)

data class TTSState(
    val isSpeaking: Boolean = false,
    val errorMessage: String? = null
)
