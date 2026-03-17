package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.repository.SettingsRepository

class SavePlayerNameUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(name: String) = repository.savePlayerName(name)
}
