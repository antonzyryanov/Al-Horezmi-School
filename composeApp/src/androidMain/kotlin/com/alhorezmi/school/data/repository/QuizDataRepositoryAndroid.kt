package com.alhorezmi.school.data.repository

import android.content.Context
import com.alhorezmi.school.domain.model.AppLanguage
import com.alhorezmi.school.domain.model.QuizTheme
import com.alhorezmi.school.AndroidContextHolder

actual suspend fun loadQuizJsonFromResources(theme: QuizTheme, language: AppLanguage): String {
    return try {
        val context = AndroidContextHolder.appContext
        val assetManager = context.assets
        val fileName = "quiz/${language.tag}/${theme.fileName}.json"
        val inputStream = assetManager.open(fileName)
        inputStream.use { it.readBytes().decodeToString() }
    } catch (e: Exception) {
        throw Exception("Failed to load quiz JSON for ${theme.fileName}/${language.tag}: ${e.message}", e)
    }
}
