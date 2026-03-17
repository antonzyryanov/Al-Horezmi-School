package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.model.PlayerRecord
import com.alhorezmi.school.domain.repository.RecordsRepository

class AddPlayerRecordUseCase(private val repository: RecordsRepository) {
    suspend operator fun invoke(record: PlayerRecord) = repository.addRecord(record)
}
