package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.model.QuizQuestion

class CheckAnswerUseCase {
    operator fun invoke(question: QuizQuestion, selectedAnswer: String): Boolean =
        question.right_answer == selectedAnswer
}
