package com.alhorezmi.school

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.alhorezmi.school.app_design.AlHorezmiTheme
import com.alhorezmi.school.data.network.IAppodealAdManager
import com.alhorezmi.school.di.initKoinIfNeeded
import com.alhorezmi.school.domain.model.AppState
import com.alhorezmi.school.presentation.localization.LocalizationRepository
import com.alhorezmi.school.presentation.screen.LevelScreen
import com.alhorezmi.school.presentation.screen.LoaderScreen
import com.alhorezmi.school.presentation.screen.MenuScreen
import com.alhorezmi.school.presentation.screen.PlayerNameScreen
import com.alhorezmi.school.presentation.screen.RecordsScreen
import com.alhorezmi.school.presentation.screen.ResultsScreen
import com.alhorezmi.school.presentation.screen.SettingsScreen
import com.alhorezmi.school.presentation.viewmodel.LevelViewModel
import com.alhorezmi.school.presentation.viewmodel.MainViewModel
import com.alhorezmi.school.presentation.viewmodel.RecordsViewModel
import com.alhorezmi.school.presentation.viewmodel.SettingsViewModel
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf

@Composable
fun App() {
    initKoinIfNeeded()
    val koin = GlobalContext.get()
    val mainViewModel = remember { koin.get<MainViewModel>() }
    val adManager = remember { koin.get<IAppodealAdManager>() }
    val mainState by mainViewModel.uiState.collectAsState()
    val strings = LocalizationRepository.strings(mainState.settings.language)

    AlHorezmiTheme {
        when (mainState.appState) {
            AppState.Loader -> LoaderScreen(strings = strings)

            AppState.PlayerNameEntering -> PlayerNameScreen(
                strings = strings,
                playerName = mainState.playerName,
                onPlayerNameChanged = mainViewModel::onPlayerNameChanged,
                onContinue = mainViewModel::onContinueTapped,
            )

            AppState.MainMenu -> MenuScreen(
                strings = strings,
                language = mainState.settings.language,
                progress = mainState.themeProgress,
                adManager = adManager,
                isBusy = mainState.isBusy,
                errorMessage = mainState.errorMessage,
                onThemeSelected = mainViewModel::openTheme,
                onRecordsSelected = mainViewModel::openRecords,
                onSettingsSelected = mainViewModel::openSettings,
            )

            AppState.LevelOpened -> {
                val section = mainState.activeSection ?: return@AlHorezmiTheme
                val session = mainState.quizSessionState
                val levelViewModel = remember(section, mainState.playerName, mainState.settings, session) {
                    koin.get<LevelViewModel> {
                        parametersOf(
                            section,
                            mainState.playerName,
                            mainState.settings,
                            mainViewModel::onLevelCompleted,
                            session,
                        )
                    }
                }
                val levelState by levelViewModel.uiState.collectAsState()
                DisposableEffect(levelViewModel) {
                    onDispose { levelViewModel.clear() }
                }
                LevelScreen(
                    strings = strings,
                    uiState = levelState,
                    adManager = adManager,
                    showRewardedAd = mainState.showRewardedAd,
                    onRewardedAdClosed = mainViewModel::onRewardedAdClosed,
                    onAnswerSelected = levelViewModel::onAnswerSelected,
                    onBackPressed = {
                        mainViewModel.quitLevel(
                            mainState.playerName,
                            section.theme_name,
                            levelState.playerProfile.score,
                            levelState.questionIndex,
                        )
                    },
                )
            }

            AppState.SettingsOpened -> {
                val settingsViewModel = remember { koin.get<SettingsViewModel>() }
                val settingsState by settingsViewModel.uiState.collectAsState()
                DisposableEffect(settingsViewModel) {
                    onDispose { settingsViewModel.clear() }
                }
                SettingsScreen(
                    strings = strings,
                    currentLanguage = settingsState.settings.language,
                    musicEnabled = settingsState.settings.musicEnabled,
                    progress = settingsState.progress,
                    onMusicChanged = settingsViewModel::setMusicEnabled,
                    onLanguageChanged = settingsViewModel::setLanguage,
                    onResetTheme = settingsViewModel::resetTheme,
                    onResetAll = settingsViewModel::resetAll,
                    onBack = mainViewModel::backToMenu,
                )
            }

            AppState.RecordBookOpened -> {
                val recordsViewModel = remember { koin.get<RecordsViewModel>() }
                val recordsState by recordsViewModel.uiState.collectAsState()
                DisposableEffect(recordsViewModel) {
                    onDispose { recordsViewModel.clear() }
                }
                RecordsScreen(
                    strings = strings,
                    records = recordsState.records,
                    onBack = mainViewModel::backToMenu,
                )
            }

            AppState.ThemeResultsOpened -> {
                val result = mainState.result ?: return@AlHorezmiTheme
                ResultsScreen(
                    strings = strings,
                    result = result,
                    onBackToMenu = mainViewModel::backToMenu,
                )
            }
        }
    }
}
