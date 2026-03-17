package com.alhorezmi.school.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alhorezmi.school.app_design.AppDimens
import com.alhorezmi.school.app_design.InkBlack
import com.alhorezmi.school.data.network.IAppodealAdManager
import com.alhorezmi.school.domain.model.AppLanguage
import com.alhorezmi.school.domain.model.QuizTheme
import com.alhorezmi.school.domain.model.ThemeProgress
import com.alhorezmi.school.generated.resources.Res
import com.alhorezmi.school.generated.resources.menu_background
import com.alhorezmi.school.presentation.components.AppodealBannerAd
import com.alhorezmi.school.presentation.components.BackgroundStage
import com.alhorezmi.school.presentation.components.MenuButton
import com.alhorezmi.school.presentation.components.ScreenCard
import com.alhorezmi.school.presentation.components.SectionTitle
import com.alhorezmi.school.presentation.localization.AppStrings
import com.alhorezmi.school.presentation.localization.LocalizationRepository

@Composable
fun MenuScreen(
    strings: AppStrings,
    language: AppLanguage,
    progress: Map<String, ThemeProgress>,
    adManager: IAppodealAdManager,
    isBusy: Boolean = false,
    errorMessage: String? = null,
    onThemeSelected: (QuizTheme) -> Unit,
    onRecordsSelected: () -> Unit,
    onSettingsSelected: () -> Unit,
) {
    val themes = QuizTheme.entries

    BackgroundStage(background = Res.drawable.menu_background) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isBusy) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = strings.loading,
                        style = MaterialTheme.typography.headlineMedium,
                        color = InkBlack,
                    )
                }
            } else if (errorMessage != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(AppDimens.screenPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    ScreenCard {
                        Text(text = "Error: $errorMessage", color = InkBlack)
                        MenuButton(title = "Back", onClick = { })
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(AppDimens.screenPadding),
                    verticalArrangement = Arrangement.spacedBy(AppDimens.contentSpacing),
                ) {
                    item { SectionTitle(strings.chooseTheme) }
                    items(themes) { theme ->
                        val themeLabel = LocalizationRepository.themeLabel(language, theme)
                        val themeProgress = progress[theme.name.lowercase()]
                        val subtitle = themeProgress?.let {
                            "Best: ${it.bestScore}/50 • Played: ${it.completedCount}"
                        }
                        MenuButton(
                            title = themeLabel,
                            subtitle = subtitle,
                            onClick = { onThemeSelected(theme) },
                        )
                    }
                    item { MenuButton(title = strings.records, onClick = onRecordsSelected) }
                    item { MenuButton(title = strings.settings, onClick = onSettingsSelected) }
                    item {
                        Spacer(modifier = Modifier.height(AppDimens.buttonHeight + AppDimens.contentSpacing))
                    }
                }
            }

            AppodealBannerAd(
                adManager = adManager,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            )
        }
    }
}
