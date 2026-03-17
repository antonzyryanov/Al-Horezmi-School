package com.alhorezmi.school.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerRecordBook(
    val records: List<PlayerRecord> = emptyList(),
)
