package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.repository.SettingsRepository

class ResetAllThemeProgressUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke() = repository.resetAllThemeProgress()
}
