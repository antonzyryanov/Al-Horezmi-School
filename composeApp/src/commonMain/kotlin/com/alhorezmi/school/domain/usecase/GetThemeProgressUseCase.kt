package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.model.QuizTheme
import com.alhorezmi.school.domain.model.ThemeProgress
import com.alhorezmi.school.domain.repository.SettingsRepository

class GetThemeProgressUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(theme: QuizTheme): ThemeProgress = repository.getThemeProgress(theme)
}
