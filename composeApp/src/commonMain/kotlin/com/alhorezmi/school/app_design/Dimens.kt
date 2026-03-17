package com.alhorezmi.school.app_design

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

object AppDimens {
    val screenPadding: Dp
        @Composable get() = LocalAdaptiveUiSpec.current.screenPadding

    val contentSpacing: Dp
        @Composable get() = LocalAdaptiveUiSpec.current.contentSpacing

    val smallSpacing: Dp
        @Composable get() = LocalAdaptiveUiSpec.current.smallSpacing

    val largeSpacing: Dp
        @Composable get() = LocalAdaptiveUiSpec.current.largeSpacing

    val cardCorner: Dp
        @Composable get() = LocalAdaptiveUiSpec.current.cardCorner

    val buttonHeight: Dp
        @Composable get() = LocalAdaptiveUiSpec.current.buttonHeight

    val scoreCorner: Dp
        @Composable get() = LocalAdaptiveUiSpec.current.scoreCorner

    val heroImage: Dp
        @Composable get() = LocalAdaptiveUiSpec.current.heroImage
}
