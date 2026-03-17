package com.alhorezmi.school.domain.repository

import com.alhorezmi.school.domain.model.PlayerRecord
import com.alhorezmi.school.domain.model.PlayerRecordBook

interface RecordsRepository {
    suspend fun addRecord(record: PlayerRecord)
    suspend fun getRecordBook(): PlayerRecordBook
    suspend fun clearRecords()
}
