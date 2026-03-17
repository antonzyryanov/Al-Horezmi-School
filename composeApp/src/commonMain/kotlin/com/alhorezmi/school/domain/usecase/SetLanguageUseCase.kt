package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.model.AppLanguage
import com.alhorezmi.school.domain.repository.SettingsRepository

class SetLanguageUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(language: AppLanguage) = repository.setLanguage(language)
}
