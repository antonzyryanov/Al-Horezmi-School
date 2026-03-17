package com.alhorezmi.school.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ThemeProgress(
    val themeName: String,
    val bestScore: Int = 0,
    val lastScore: Int = 0,
    val completedCount: Int = 0,
)
