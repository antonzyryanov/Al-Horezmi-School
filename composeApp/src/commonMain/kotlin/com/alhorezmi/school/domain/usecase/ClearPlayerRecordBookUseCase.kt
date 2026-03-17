package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.repository.RecordsRepository

class ClearPlayerRecordBookUseCase(private val repository: RecordsRepository) {
    suspend operator fun invoke() = repository.clearRecords()
}
