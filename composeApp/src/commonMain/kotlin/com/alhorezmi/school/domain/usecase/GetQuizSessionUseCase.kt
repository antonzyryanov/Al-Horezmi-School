package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.model.QuizSessionState
import com.alhorezmi.school.domain.model.QuizTheme
import com.alhorezmi.school.domain.repository.SettingsRepository

class GetQuizSessionUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(theme: QuizTheme): QuizSessionState? = repository.getQuizSession(theme)
}
