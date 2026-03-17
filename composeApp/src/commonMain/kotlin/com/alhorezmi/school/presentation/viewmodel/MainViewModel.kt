package com.alhorezmi.school.presentation.viewmodel

import com.alhorezmi.school.data.storage.MusicPlayerService
import com.alhorezmi.school.domain.model.AppSettings
import com.alhorezmi.school.domain.model.AppState
import com.alhorezmi.school.domain.model.PlayerRecord
import com.alhorezmi.school.domain.model.QuizResult
import com.alhorezmi.school.domain.model.QuizSection
import com.alhorezmi.school.domain.model.QuizSessionState
import com.alhorezmi.school.domain.model.QuizTheme
import com.alhorezmi.school.domain.model.ThemeProgress
import com.alhorezmi.school.domain.usecase.AddPlayerRecordUseCase
import com.alhorezmi.school.domain.usecase.ClearQuizSessionUseCase
import com.alhorezmi.school.domain.usecase.CreateThemeProgressUseCase
import com.alhorezmi.school.domain.usecase.GetAllThemeProgressUseCase
import com.alhorezmi.school.domain.usecase.GetQuizSessionUseCase
import com.alhorezmi.school.domain.usecase.GetThemeProgressUseCase
import com.alhorezmi.school.domain.usecase.LoadQuizSectionUseCase
import com.alhorezmi.school.domain.usecase.ObserveSettingsUseCase
import com.alhorezmi.school.domain.usecase.SavePlayerNameUseCase
import com.alhorezmi.school.domain.usecase.SaveQuizSessionUseCase
import com.alhorezmi.school.domain.usecase.SaveThemeProgressUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class MainUiState(
    val appState: AppState = AppState.Loader,
    val playerName: String = "",
    val settings: AppSettings = AppSettings(),
    val activeSection: QuizSection? = null,
    val themeProgress: Map<String, ThemeProgress> = emptyMap(),
    val result: QuizResult? = null,
    val isBusy: Boolean = false,
    val errorMessage: String? = null,
    val quizSessionState: QuizSessionState? = null,
    val showRewardedAd: Boolean = false,
)

class MainViewModel(
    private val loadQuizSectionUseCase: LoadQuizSectionUseCase,
    private val savePlayerNameUseCase: SavePlayerNameUseCase,
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val getThemeProgressUseCase: GetThemeProgressUseCase,
    private val getAllThemeProgressUseCase: GetAllThemeProgressUseCase,
    private val saveThemeProgressUseCase: SaveThemeProgressUseCase,
    private val createThemeProgressUseCase: CreateThemeProgressUseCase,
    private val addPlayerRecordUseCase: AddPlayerRecordUseCase,
    private val getQuizSessionUseCase: GetQuizSessionUseCase,
    private val saveQuizSessionUseCase: SaveQuizSessionUseCase,
    private val clearQuizSessionUseCase: ClearQuizSessionUseCase,
    private val musicPlayerService: MusicPlayerService,
) : BaseViewModel() {

    private enum class MusicMode {
        NONE,
        MENU,
        LEVEL,
    }

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    private var sectionCache: Map<QuizTheme, QuizSection> = emptyMap()
    private var musicMode: MusicMode = MusicMode.NONE

    init {
        observeSettings()
        refreshThemeProgress()
        viewModelScope.launch {
            delay(5_000)
            _uiState.value = _uiState.value.copy(appState = AppState.PlayerNameEntering)
            playMenuLoopIfNeeded()
        }
    }

    fun onPlayerNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(playerName = name)
    }

    fun onContinueTapped() {
        val currentName = _uiState.value.playerName.trim()
        if (currentName.isBlank()) return
        viewModelScope.launch {
            savePlayerNameUseCase(currentName)
            _uiState.value = _uiState.value.copy(playerName = currentName, appState = AppState.MainMenu)
            playMenuLoopIfNeeded()
            preloadThemeSections()
        }
    }

    fun openTheme(theme: QuizTheme) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isBusy = true, errorMessage = null)
            stopMusicIfNeeded()
            musicMode = MusicMode.LEVEL
            val cached = sectionCache[theme]
            val loadedSection = cached ?: runCatching {
                loadQuizSectionUseCase(theme, _uiState.value.settings.language)
            }.getOrNull()
            if (loadedSection != null) {
                sectionCache = sectionCache + (theme to loadedSection)
                val session = getQuizSessionUseCase(theme)
                _uiState.value = _uiState.value.copy(
                    activeSection = loadedSection,
                    appState = AppState.LevelOpened,
                    isBusy = false,
                    quizSessionState = session,
                    showRewardedAd = true,
                )
            } else {
                playMenuLoopIfNeeded()
                _uiState.value = _uiState.value.copy(
                    isBusy = false,
                    errorMessage = "Failed to load section for ${theme.name}",
                )
            }
        }
    }

    fun onRewardedAdClosed() {
        _uiState.value = _uiState.value.copy(showRewardedAd = false)
    }

    fun openSettings() {
        _uiState.value = _uiState.value.copy(appState = AppState.SettingsOpened)
    }

    fun openRecords() {
        _uiState.value = _uiState.value.copy(appState = AppState.RecordBookOpened)
    }

    fun backToMenu() {
        _uiState.value = _uiState.value.copy(
            appState = AppState.MainMenu,
            activeSection = null,
            result = null,
            errorMessage = null,
            quizSessionState = null,
            showRewardedAd = false,
        )
        playMenuLoopIfNeeded()
        refreshThemeProgress()
    }

    fun onLevelCompleted(result: QuizResult) {
        viewModelScope.launch {
            val theme = QuizTheme.fromThemeName(result.themeName)
            val previous = getThemeProgressUseCase(theme)
            val updatedProgress = createThemeProgressUseCase(previous, result)
            saveThemeProgressUseCase(updatedProgress)
            addPlayerRecordUseCase(
                PlayerRecord(
                    player_name = result.playerName,
                    theme_name = result.themeName,
                    score = result.score.toString(),
                )
            )
            clearQuizSessionUseCase(theme)
            _uiState.value = _uiState.value.copy(
                result = result,
                appState = AppState.ThemeResultsOpened,
                activeSection = null,
                quizSessionState = null,
            )
            refreshThemeProgress()
        }
    }

    fun quitLevel(playerName: String, themeName: String, currentScore: Int, currentQuestionIndex: Int) {
        viewModelScope.launch {
            val theme = QuizTheme.fromThemeName(themeName)
            val session = QuizSessionState(
                themeName = themeName,
                questionIndex = currentQuestionIndex,
                currentScore = currentScore,
            )
            saveQuizSessionUseCase(session)
            _uiState.value = _uiState.value.copy(
                appState = AppState.MainMenu,
                activeSection = null,
                result = null,
                errorMessage = null,
                quizSessionState = null,
                showRewardedAd = false,
            )
            playMenuLoopIfNeeded()
            refreshThemeProgress()
        }
    }

    private fun observeSettings() {
        viewModelScope.launch {
            observeSettingsUseCase().collectLatest { settings ->
                _uiState.value = _uiState.value.copy(settings = settings)
                if (!settings.musicEnabled) {
                    stopMusicIfNeeded()
                } else if (_uiState.value.appState != AppState.LevelOpened) {
                    playMenuLoopIfNeeded()
                }
                if (_uiState.value.appState == AppState.MainMenu && sectionCache.isEmpty()) {
                    preloadThemeSections()
                }
            }
        }
    }

    private fun playMenuLoopIfNeeded() {
        if (!_uiState.value.settings.musicEnabled) return
        if (musicMode == MusicMode.MENU) return
        musicPlayerService.playMenuLoop()
        musicMode = MusicMode.MENU
    }

    private fun stopMusicIfNeeded() {
        if (musicMode == MusicMode.NONE) return
        musicPlayerService.stop()
        musicMode = MusicMode.NONE
    }

    override fun clear() {
        stopMusicIfNeeded()
        musicPlayerService.release()
        super.clear()
    }

    private fun preloadThemeSections() {
        viewModelScope.launch {
            val language = _uiState.value.settings.language
            val loaded = QuizTheme.entries.mapNotNull { theme ->
                runCatching {
                    theme to loadQuizSectionUseCase(theme, language)
                }.getOrNull()
            }.toMap()
            if (loaded.isNotEmpty()) {
                sectionCache = loaded
            }
        }
    }

    private fun refreshThemeProgress() {
        viewModelScope.launch {
            val progress = getAllThemeProgressUseCase().associateBy { it.themeName.lowercase() }
            _uiState.value = _uiState.value.copy(themeProgress = progress)
        }
    }
}
