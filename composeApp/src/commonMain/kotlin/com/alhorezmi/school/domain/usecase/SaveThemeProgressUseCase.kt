package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.model.ThemeProgress
import com.alhorezmi.school.domain.repository.SettingsRepository

class SaveThemeProgressUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(progress: ThemeProgress) = repository.saveThemeProgress(progress)
}
