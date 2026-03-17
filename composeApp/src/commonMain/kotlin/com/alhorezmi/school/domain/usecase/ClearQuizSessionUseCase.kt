package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.model.QuizTheme
import com.alhorezmi.school.domain.repository.SettingsRepository

class ClearQuizSessionUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(theme: QuizTheme) = repository.clearQuizSession(theme)
}
