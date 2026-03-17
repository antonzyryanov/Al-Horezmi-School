package com.alhorezmi.school.presentation.viewmodel

import com.alhorezmi.school.domain.model.AppLanguage
import com.alhorezmi.school.domain.model.AppSettings
import com.alhorezmi.school.domain.model.QuizTheme
import com.alhorezmi.school.domain.model.ThemeProgress
import com.alhorezmi.school.domain.usecase.GetAllThemeProgressUseCase
import com.alhorezmi.school.domain.usecase.ObserveSettingsUseCase
import com.alhorezmi.school.domain.usecase.ResetAllThemeProgressUseCase
import com.alhorezmi.school.domain.usecase.ResetThemeProgressUseCase
import com.alhorezmi.school.domain.usecase.SetLanguageUseCase
import com.alhorezmi.school.domain.usecase.SetMusicEnabledUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class SettingsUiState(
    val settings: AppSettings = AppSettings(),
    val progress: List<ThemeProgress> = emptyList(),
)

class SettingsViewModel(
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val setMusicEnabledUseCase: SetMusicEnabledUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val getAllThemeProgressUseCase: GetAllThemeProgressUseCase,
    private val resetThemeProgressUseCase: ResetThemeProgressUseCase,
    private val resetAllThemeProgressUseCase: ResetAllThemeProgressUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        refreshProgress()
        viewModelScope.launch {
            observeSettingsUseCase().collectLatest { settings ->
                _uiState.value = _uiState.value.copy(settings = settings)
            }
        }
    }

    fun setMusicEnabled(enabled: Boolean) {
        viewModelScope.launch {
            setMusicEnabledUseCase(enabled)
        }
    }

    fun setLanguage(language: AppLanguage) {
        viewModelScope.launch {
            setLanguageUseCase(language)
        }
    }

    fun resetTheme(theme: QuizTheme) {
        viewModelScope.launch {
            resetThemeProgressUseCase(theme)
            refreshProgress()
        }
    }

    fun resetAll() {
        viewModelScope.launch {
            resetAllThemeProgressUseCase()
            refreshProgress()
        }
    }

    private fun refreshProgress() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(progress = getAllThemeProgressUseCase())
        }
    }
}
