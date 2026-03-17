package com.alhorezmi.school.data.repository

import com.alhorezmi.school.domain.model.PlayerRecord
import com.alhorezmi.school.domain.model.PlayerRecordBook
import com.alhorezmi.school.domain.repository.RecordsRepository
import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json

class LocalRecordsRepository(
    private val settings: Settings,
    private val json: Json,
) : RecordsRepository {

    override suspend fun addRecord(record: PlayerRecord) {
        val current = getRecordBook().records
        val updated = PlayerRecordBook(
            records = (current + record).sortedByDescending { it.score.toInt() }
        )
        settings.putString(KEY_RECORD_BOOK, json.encodeToString(PlayerRecordBook.serializer(), updated))
    }

    override suspend fun getRecordBook(): PlayerRecordBook {
        val raw = settings.getStringOrNull(KEY_RECORD_BOOK) ?: return PlayerRecordBook()
        return json.decodeFromString(PlayerRecordBook.serializer(), raw)
    }

    override suspend fun clearRecords() {
        settings.remove(KEY_RECORD_BOOK)
    }

    private companion object {
        const val KEY_RECORD_BOOK = "player_record_book"
    }
}
