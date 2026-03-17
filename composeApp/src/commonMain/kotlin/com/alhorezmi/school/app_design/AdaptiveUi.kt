package com.alhorezmi.school.app_design

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Immutable
data class AdaptiveUiSpec(
    val screenPadding: Dp,
    val contentSpacing: Dp,
    val smallSpacing: Dp,
    val largeSpacing: Dp,
    val cardCorner: Dp,
    val buttonHeight: Dp,
    val scoreCorner: Dp,
    val heroImage: Dp,
    val fontScale: Float,
)

private val compactSpec = AdaptiveUiSpec(
    screenPadding = 14.dp,
    contentSpacing = 12.dp,
    smallSpacing = 6.dp,
    largeSpacing = 18.dp,
    cardCorner = 18.dp,
    buttonHeight = 48.dp,
    scoreCorner = 16.dp,
    heroImage = 160.dp,
    fontScale = 0.9f,
)

private val mediumSpec = AdaptiveUiSpec(
    screenPadding = 20.dp,
    contentSpacing = 16.dp,
    smallSpacing = 8.dp,
    largeSpacing = 24.dp,
    cardCorner = 22.dp,
    buttonHeight = 54.dp,
    scoreCorner = 18.dp,
    heroImage = 200.dp,
    fontScale = 1f,
)

private val expandedSpec = AdaptiveUiSpec(
    screenPadding = 28.dp,
    contentSpacing = 20.dp,
    smallSpacing = 10.dp,
    largeSpacing = 30.dp,
    cardCorner = 24.dp,
    buttonHeight = 58.dp,
    scoreCorner = 20.dp,
    heroImage = 240.dp,
    fontScale = 1.08f,
)

val LocalAdaptiveUiSpec = compositionLocalOf { mediumSpec }

fun adaptiveUiSpecForWidth(maxWidth: Dp): AdaptiveUiSpec = when {
    maxWidth < 360.dp -> compactSpec
    maxWidth < 700.dp -> mediumSpec
    else -> expandedSpec
}

@Composable
fun scaledText(base: TextUnit): TextUnit {
    val scale = LocalAdaptiveUiSpec.current.fontScale
    return (base.value * scale).sp
}
