package com.alhorezmi.school.domain.usecase

class BuildResultQuoteUseCase {
    operator fun invoke(score: Int, low: String, medium: String, high: String): String = when {
        score < 25 -> low
        score < 40 -> medium
        else -> high
    }
}
