package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.model.AppSettings
import com.alhorezmi.school.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class ObserveSettingsUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<AppSettings> = repository.observeSettings()
}
