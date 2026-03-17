package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.model.PlayerRecordBook
import com.alhorezmi.school.domain.repository.RecordsRepository

class GetPlayerRecordBookUseCase(private val repository: RecordsRepository) {
    suspend operator fun invoke(): PlayerRecordBook = repository.getRecordBook()
}
