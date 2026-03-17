package com.alhorezmi.school.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.alhorezmi.school.app_design.AppDimens
import com.alhorezmi.school.app_design.InkBlack
import com.alhorezmi.school.domain.model.AppLanguage
import com.alhorezmi.school.domain.model.QuizTheme
import com.alhorezmi.school.domain.model.ThemeProgress
import com.alhorezmi.school.generated.resources.Res
import com.alhorezmi.school.generated.resources.menu_background
import com.alhorezmi.school.presentation.components.BackgroundStage
import com.alhorezmi.school.presentation.components.PrimaryActionButton
import com.alhorezmi.school.presentation.components.ScreenCard
import com.alhorezmi.school.presentation.components.SectionTitle
import com.alhorezmi.school.presentation.localization.AppStrings
import com.alhorezmi.school.presentation.localization.LocalizationRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    strings: AppStrings,
    currentLanguage: AppLanguage,
    musicEnabled: Boolean,
    progress: List<ThemeProgress>,
    onMusicChanged: (Boolean) -> Unit,
    onLanguageChanged: (AppLanguage) -> Unit,
    onResetTheme: (QuizTheme) -> Unit,
    onResetAll: () -> Unit,
    onBack: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    BackgroundStage(background = Res.drawable.menu_background) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimens.screenPadding),
            verticalArrangement = Arrangement.spacedBy(AppDimens.contentSpacing),
        ) {
            item {
                SectionTitle(strings.settings)
            }
            item {
                ScreenCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(strings.music, color = InkBlack)
                        Switch(checked = musicEnabled, onCheckedChange = onMusicChanged)
                    }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                    ) {
                        OutlinedTextField(
                            value = currentLanguage.tag.uppercase(),
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(strings.language) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            AppLanguage.entries.forEach { language ->
                                DropdownMenuItem(
                                    text = { Text(language.tag.uppercase()) },
                                    onClick = {
                                        expanded = false
                                        onLanguageChanged(language)
                                    },
                                )
                            }
                        }
                    }
                    PrimaryActionButton(
                        text = strings.resetAll,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onResetAll,
                    )
                }
            }
            item {
                ScreenCard(modifier = Modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(AppDimens.contentSpacing)) {
                        progress.forEach { item ->
                            val theme = QuizTheme.fromThemeName(item.themeName)
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(AppDimens.smallSpacing),
                            ) {
                                Text(
                                    text = "${LocalizationRepository.themeLabel(currentLanguage, theme)} • ${item.bestScore}/50",
                                    color = InkBlack,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                                PrimaryActionButton(
                                    text = strings.resetTheme,
                                    modifier = Modifier.align(Alignment.Start),
                                    onClick = { onResetTheme(theme) },
                                )
                            }
                        }
                    }
                }
            }
            item {
                PrimaryActionButton(
                    text = strings.backToMenu,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onBack,
                )
            }
        }
    }
}
