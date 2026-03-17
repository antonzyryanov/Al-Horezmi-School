package com.alhorezmi.school.di

import com.alhorezmi.school.data.repository.LocalRecordsRepository
import com.alhorezmi.school.data.repository.LocalSettingsRepository
import com.alhorezmi.school.data.repository.QuizDataRepository
import com.alhorezmi.school.data.network.AppodealAdManager
import com.alhorezmi.school.data.network.IAppodealAdManager
import com.alhorezmi.school.data.storage.createMusicPlayerService
import com.alhorezmi.school.domain.model.AppSettings
import com.alhorezmi.school.domain.model.QuizSection
import com.alhorezmi.school.domain.repository.DataRepository
import com.alhorezmi.school.domain.repository.RecordsRepository
import com.alhorezmi.school.domain.repository.SettingsRepository
import com.alhorezmi.school.domain.usecase.AddPlayerRecordUseCase
import com.alhorezmi.school.domain.usecase.BuildResultQuoteUseCase
import com.alhorezmi.school.domain.usecase.CheckAnswerUseCase
import com.alhorezmi.school.domain.usecase.ClearPlayerRecordBookUseCase
import com.alhorezmi.school.domain.usecase.ClearQuizSessionUseCase
import com.alhorezmi.school.domain.usecase.CreateThemeProgressUseCase
import com.alhorezmi.school.domain.usecase.GetAllThemeProgressUseCase
import com.alhorezmi.school.domain.usecase.GetPlayerNameUseCase
import com.alhorezmi.school.domain.usecase.GetPlayerRecordBookUseCase
import com.alhorezmi.school.domain.usecase.GetQuizSessionUseCase
import com.alhorezmi.school.domain.usecase.GetThemeProgressUseCase
import com.alhorezmi.school.domain.usecase.LoadQuizSectionUseCase
import com.alhorezmi.school.domain.usecase.ObserveSettingsUseCase
import com.alhorezmi.school.domain.usecase.ResetAllThemeProgressUseCase
import com.alhorezmi.school.domain.usecase.ResetThemeProgressUseCase
import com.alhorezmi.school.domain.usecase.SavePlayerNameUseCase
import com.alhorezmi.school.domain.usecase.SaveQuizSessionUseCase
import com.alhorezmi.school.domain.usecase.SaveThemeProgressUseCase
import com.alhorezmi.school.domain.usecase.SetLanguageUseCase
import com.alhorezmi.school.domain.usecase.SetMusicEnabledUseCase
import com.alhorezmi.school.presentation.viewmodel.LevelViewModel
import com.alhorezmi.school.presentation.viewmodel.MainViewModel
import com.alhorezmi.school.presentation.viewmodel.RecordsViewModel
import com.alhorezmi.school.presentation.viewmodel.SettingsViewModel
import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val appModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }
    single { Settings() }
    single<DataRepository> { QuizDataRepository(get()) }
    single<SettingsRepository> { LocalSettingsRepository(get(), get()) }
    single<RecordsRepository> { LocalRecordsRepository(get(), get()) }

    single { LoadQuizSectionUseCase(get()) }
    single { SavePlayerNameUseCase(get()) }
    single { GetPlayerNameUseCase(get()) }
    single { ObserveSettingsUseCase(get()) }
    single { SetMusicEnabledUseCase(get()) }
    single { SetLanguageUseCase(get()) }
    single { SaveThemeProgressUseCase(get()) }
    single { GetThemeProgressUseCase(get()) }
    single { GetAllThemeProgressUseCase(get()) }
    single { ResetThemeProgressUseCase(get()) }
    single { ResetAllThemeProgressUseCase(get()) }
    single { AddPlayerRecordUseCase(get()) }
    single { GetPlayerRecordBookUseCase(get()) }
    single { ClearPlayerRecordBookUseCase(get()) }
    single { GetQuizSessionUseCase(get()) }
    single { SaveQuizSessionUseCase(get()) }
    single { ClearQuizSessionUseCase(get()) }
    single { CheckAnswerUseCase() }
    single { CreateThemeProgressUseCase() }
    single { BuildResultQuoteUseCase() }

    single { createMusicPlayerService() }
    single<IAppodealAdManager> {
        AppodealAdManager()
    }

    single {
        MainViewModel(
            loadQuizSectionUseCase = get(),
            savePlayerNameUseCase = get(),
            observeSettingsUseCase = get(),
            getThemeProgressUseCase = get(),
            getAllThemeProgressUseCase = get(),
            saveThemeProgressUseCase = get(),
            createThemeProgressUseCase = get(),
            addPlayerRecordUseCase = get(),
            getQuizSessionUseCase = get(),
            saveQuizSessionUseCase = get(),
            clearQuizSessionUseCase = get(),
            musicPlayerService = get(),
        )
    }

    factory {
        SettingsViewModel(
            observeSettingsUseCase = get(),
            setMusicEnabledUseCase = get(),
            setLanguageUseCase = get(),
            getAllThemeProgressUseCase = get(),
            resetThemeProgressUseCase = get(),
            resetAllThemeProgressUseCase = get(),
        )
    }

    factory {
        RecordsViewModel(
            getPlayerRecordBookUseCase = get(),
        )
    }

    factory { (section: QuizSection, playerName: String, settings: AppSettings, onCompleted: (com.alhorezmi.school.domain.model.QuizResult) -> Unit, session: com.alhorezmi.school.domain.model.QuizSessionState?) ->
        LevelViewModel(
            quizSection = section,
            playerName = playerName,
            appSettings = settings,
            musicPlayerService = get(),
            checkAnswerUseCase = get(),
            onLevelCompleted = onCompleted,
            initialQuestionIndex = session?.questionIndex ?: 0,
            initialScore = session?.currentScore ?: 0,
        )
    }
}

fun initKoinIfNeeded() {
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            modules(appModule)
        }
    }
}
