package com.alhorezmi.school.data.repository

import com.alhorezmi.school.domain.model.AppLanguage
import com.alhorezmi.school.domain.model.QuizSection
import com.alhorezmi.school.domain.model.QuizTheme
import com.alhorezmi.school.domain.repository.DataRepository
import kotlinx.serialization.json.Json

class QuizDataRepository(
    private val json: Json,
) : DataRepository {

    override suspend fun loadQuizSection(theme: QuizTheme, language: AppLanguage): QuizSection {
        val jsonString = loadQuizJsonFromResources(theme, language)
        return json.decodeFromString(QuizSection.serializer(), jsonString)
    }
}

expect suspend fun loadQuizJsonFromResources(theme: QuizTheme, language: AppLanguage): String
