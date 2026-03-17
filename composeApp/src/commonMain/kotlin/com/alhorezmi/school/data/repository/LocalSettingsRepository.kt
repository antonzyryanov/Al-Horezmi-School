package com.alhorezmi.school.data.repository

import com.alhorezmi.school.domain.model.AppLanguage
import com.alhorezmi.school.domain.model.AppSettings
import com.alhorezmi.school.domain.model.QuizTheme
import com.alhorezmi.school.domain.model.QuizSessionState
import com.alhorezmi.school.domain.model.ThemeProgress
import com.alhorezmi.school.domain.repository.SettingsRepository
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class LocalSettingsRepository(
    private val settings: Settings,
    private val json: Json,
) : SettingsRepository {

    private val settingsFlow = MutableStateFlow(readSettings())

    override suspend fun savePlayerName(name: String) {
        settings.putString(KEY_PLAYER_NAME, name)
    }

    override suspend fun getPlayerName(): String = settings.getString(KEY_PLAYER_NAME, "")

    override suspend fun setMusicEnabled(enabled: Boolean) {
        settings.putBoolean(KEY_MUSIC_ENABLED, enabled)
        settingsFlow.value = readSettings()
    }

    override suspend fun setLanguage(language: AppLanguage) {
        settings.putString(KEY_LANGUAGE, language.tag)
        settingsFlow.value = readSettings()
    }

    override suspend fun saveThemeProgress(progress: ThemeProgress) {
        settings.putString(progressKey(progress.themeName), json.encodeToString(ThemeProgress.serializer(), progress))
    }

    override suspend fun resetThemeProgress(theme: QuizTheme) {
        settings.remove(progressKey(theme.name))
        settings.remove(progressKey(theme.fileName))
    }

    override suspend fun resetAllThemeProgress() {
        QuizTheme.entries.forEach { theme ->
            resetThemeProgress(theme)
        }
    }

    override suspend fun getThemeProgress(theme: QuizTheme): ThemeProgress {
        val saved = settings.getStringOrNull(progressKey(theme.name))
            ?: settings.getStringOrNull(progressKey(theme.fileName))
            ?: return ThemeProgress(themeName = theme.name)
        return json.decodeFromString(ThemeProgress.serializer(), saved)
    }

    override suspend fun getAllThemeProgress(): List<ThemeProgress> =
        QuizTheme.entries.map { getThemeProgress(it) }

    override suspend fun saveQuizSession(session: QuizSessionState) {
        settings.putString(sessionKey(session.themeName), json.encodeToString(QuizSessionState.serializer(), session))
    }

    override suspend fun getQuizSession(theme: QuizTheme): QuizSessionState? {
        val saved = settings.getStringOrNull(sessionKey(theme.name))
            ?: settings.getStringOrNull(sessionKey(theme.fileName))
            ?: return null
        return json.decodeFromString(QuizSessionState.serializer(), saved)
    }

    override suspend fun clearQuizSession(theme: QuizTheme) {
        settings.remove(sessionKey(theme.name))
        settings.remove(sessionKey(theme.fileName))
    }

    override fun observeSettings(): Flow<AppSettings> = settingsFlow.asStateFlow()

    private fun readSettings(): AppSettings = AppSettings(
        musicEnabled = settings.getBoolean(KEY_MUSIC_ENABLED, true),
        language = AppLanguage.fromTag(settings.getString(KEY_LANGUAGE, AppLanguage.ENGLISH.tag)),
    )

    private fun progressKey(themeName: String): String = "theme_progress_${themeName.lowercase()}"

    private fun sessionKey(themeName: String): String = "quiz_session_${themeName.lowercase()}"

    private companion object {
        const val KEY_PLAYER_NAME = "player_name"
        const val KEY_MUSIC_ENABLED = "music_enabled"
        const val KEY_LANGUAGE = "language"
    }
}
