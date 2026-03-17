package com.alhorezmi.school.domain.repository

import com.alhorezmi.school.domain.model.AppLanguage
import com.alhorezmi.school.domain.model.AppSettings
import com.alhorezmi.school.domain.model.QuizSessionState
import com.alhorezmi.school.domain.model.QuizTheme
import com.alhorezmi.school.domain.model.ThemeProgress
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun savePlayerName(name: String)
    suspend fun getPlayerName(): String
    suspend fun setMusicEnabled(enabled: Boolean)
    suspend fun setLanguage(language: AppLanguage)
    suspend fun saveThemeProgress(progress: ThemeProgress)
    suspend fun resetThemeProgress(theme: QuizTheme)
    suspend fun resetAllThemeProgress()
    suspend fun getThemeProgress(theme: QuizTheme): ThemeProgress
    suspend fun getAllThemeProgress(): List<ThemeProgress>
    suspend fun saveQuizSession(session: QuizSessionState)
    suspend fun getQuizSession(theme: QuizTheme): QuizSessionState?
    suspend fun clearQuizSession(theme: QuizTheme)
    fun observeSettings(): Flow<AppSettings>
}
