package com.alhorezmi.school.domain.usecase

import com.alhorezmi.school.domain.model.AppLanguage
import com.alhorezmi.school.domain.model.QuizSection
import com.alhorezmi.school.domain.model.QuizTheme
import com.alhorezmi.school.domain.repository.DataRepository

class LoadQuizSectionUseCase(private val repository: DataRepository) {
    suspend operator fun invoke(theme: QuizTheme, language: AppLanguage): QuizSection =
        repository.loadQuizSection(theme, language)
}
