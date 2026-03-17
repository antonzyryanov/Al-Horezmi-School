package com.alhorezmi.school.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizSessionState(
    val themeName: String,
    val questionIndex: Int,
    val currentScore: Int,
    val timestamp: Long = System.currentTimeMillis(),
)
