package com.alhorezmi.school.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerRecord(
    val player_name: String,
    val theme_name: String,
    val score: String,
)
