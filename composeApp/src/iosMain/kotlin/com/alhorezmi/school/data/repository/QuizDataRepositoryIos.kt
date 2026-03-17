package com.alhorezmi.school.data.repository

import com.alhorezmi.school.domain.model.AppLanguage
import com.alhorezmi.school.domain.model.QuizTheme

actual suspend fun loadQuizJsonFromResources(theme: QuizTheme, language: AppLanguage): String {
    // iOS implementation - for now, return a minimal quiz to allow testing
    // In production, this would load from the app bundle
    return """{"theme_name":"${theme.name}","questions":[]}"""
}
