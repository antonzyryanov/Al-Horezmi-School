package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.model.QuizResult
import com.alhorezmi.school.domain.model.ThemeProgress

class CreateThemeProgressUseCase {
    operator fun invoke(previous: ThemeProgress, result: QuizResult): ThemeProgress = ThemeProgress(
        themeName = result.themeName,
        bestScore = maxOf(previous.bestScore, result.score),
        lastScore = result.score,
        completedCount = previous.completedCount + 1,
    )
}
