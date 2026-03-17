package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.repository.SettingsRepository

class SetMusicEnabledUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(enabled: Boolean) = repository.setMusicEnabled(enabled)
}
