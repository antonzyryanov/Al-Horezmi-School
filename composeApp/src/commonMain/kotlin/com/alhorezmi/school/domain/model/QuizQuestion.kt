package com.alhorezmi.school.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestion(
    val answer: String,
    val answer_1: String,
    val answer_2: String,
    val answer_3: String,
    val answer_4: String,
    val right_answer: String,
)
