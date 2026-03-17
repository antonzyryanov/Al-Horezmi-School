package com.alhorezmi.school.presentation.viewmodel

import com.alhorezmi.school.data.storage.MusicPlayerService
import com.alhorezmi.school.domain.model.AppLanguage
import com.alhorezmi.school.domain.model.AppSettings
import com.alhorezmi.school.domain.model.PlayerProfile
import com.alhorezmi.school.domain.model.QuizQuestion
import com.alhorezmi.school.domain.model.QuizResult
import com.alhorezmi.school.domain.model.QuizSection
import com.alhorezmi.school.domain.usecase.CheckAnswerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class LevelUiState(
    val playerProfile: PlayerProfile = PlayerProfile(),
    val themeName: String = "",
    val questionIndex: Int = 0,
    val currentQuestion: QuizQuestion? = null,
    val totalQuestions: Int = 0,
    val language: AppLanguage = AppLanguage.ENGLISH,
)

class LevelViewModel(
    private val quizSection: QuizSection,
    private val playerName: String,
    private val appSettings: AppSettings,
    private val musicPlayerService: MusicPlayerService,
    private val checkAnswerUseCase: CheckAnswerUseCase,
    private val onLevelCompleted: (QuizResult) -> Unit,
    private val initialQuestionIndex: Int = 0,
    private val initialScore: Int = 0,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(
        LevelUiState(
            playerProfile = PlayerProfile(name = playerName, score = initialScore),
            themeName = quizSection.theme_name,
            questionIndex = initialQuestionIndex,
            currentQuestion = quizSection.questions.getOrNull(initialQuestionIndex),
            totalQuestions = quizSection.questions.size,
            language = appSettings.language,
        )
    )
    val uiState: StateFlow<LevelUiState> = _uiState.asStateFlow()

    init {
        if (appSettings.musicEnabled) {
            musicPlayerService.playLevelLoop()
        }
    }

    fun onAnswerSelected(selectedAnswer: String) {
        val state = _uiState.value
        val currentQuestion = state.currentQuestion ?: return
        val isCorrect = checkAnswerUseCase(currentQuestion, selectedAnswer)
        val newScore = if (isCorrect) state.playerProfile.score + 1 else state.playerProfile.score
        val nextIndex = state.questionIndex + 1
        if (nextIndex >= quizSection.questions.size) {
            musicPlayerService.stop()
            onLevelCompleted(
                QuizResult(
                    playerName = playerName,
                    themeName = quizSection.theme_name,
                    score = newScore,
                )
            )
            return
        }

        _uiState.value = state.copy(
            playerProfile = state.playerProfile.copy(score = newScore),
            questionIndex = nextIndex,
            currentQuestion = quizSection.questions[nextIndex],
        )
    }

    override fun clear() {
        musicPlayerService.stop()
        super.clear()
    }
}
