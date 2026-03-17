package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.model.QuizSessionState
import com.alhorezmi.school.domain.repository.SettingsRepository

class SaveQuizSessionUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(session: QuizSessionState) = repository.saveQuizSession(session)
}
