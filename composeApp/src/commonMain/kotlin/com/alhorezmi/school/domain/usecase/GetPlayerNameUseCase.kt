package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.repository.SettingsRepository

class GetPlayerNameUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(): String = repository.getPlayerName()
}
