package com.alhorezmi.school.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizSection(
    val theme_name: String,
    val questions: List<QuizQuestion>,
)
