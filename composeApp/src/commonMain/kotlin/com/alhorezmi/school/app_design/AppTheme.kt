package com.alhorezmi.school.app_design

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightScheme = lightColorScheme(
    primary = SandYellow,
    onPrimary = InkBlack,
    secondary = DarkSandYellow,
    onSecondary = InkBlack,
    tertiary = WoodBrown,
    onTertiary = WarmPaper,
    background = WarmPaper,
    onBackground = InkBlack,
    surface = CardSand,
    onSurface = InkBlack,
    primaryContainer = DarkSandYellow,
    secondaryContainer = SandYellow,
)

private val DarkScheme = darkColorScheme(
    primary = DarkSandYellow,
    onPrimary = WarmPaper,
    secondary = WoodBrown,
    onSecondary = WarmPaper,
    tertiary = SandYellow,
    onTertiary = InkBlack,
    background = DeepWood,
    onBackground = WarmPaper,
    surface = WoodBrown,
    onSurface = WarmPaper,
)

@Composable
fun AlHorezmiTheme(
    content: @Composable () -> Unit,
) {
    BoxWithConstraints {
        val adaptiveSpec = adaptiveUiSpecForWidth(maxWidth)
        CompositionLocalProvider(LocalAdaptiveUiSpec provides adaptiveSpec) {
            MaterialTheme(
                colorScheme = if (isSystemInDarkTheme()) DarkScheme else LightScheme,
                typography = appTypography(),
                content = content,
            )
        }
    }
}
