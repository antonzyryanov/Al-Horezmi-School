package com.alhorezmi.school.domain.repository

import com.alhorezmi.school.domain.model.AppLanguage
import com.alhorezmi.school.domain.model.QuizSection
import com.alhorezmi.school.domain.model.QuizTheme

interface DataRepository {
    suspend fun loadQuizSection(theme: QuizTheme, language: AppLanguage): QuizSection
}
