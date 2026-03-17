package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.model.ThemeProgress
import com.alhorezmi.school.domain.repository.SettingsRepository

class GetAllThemeProgressUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(): List<ThemeProgress> = repository.getAllThemeProgress()
}
