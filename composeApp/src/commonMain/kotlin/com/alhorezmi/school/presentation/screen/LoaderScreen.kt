package com.alhorezmi.school.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alhorezmi.school.app_design.AppDimens
import com.alhorezmi.school.generated.resources.Res
import com.alhorezmi.school.generated.resources.loader_background
import com.alhorezmi.school.presentation.components.BackgroundStage
import com.alhorezmi.school.presentation.components.LoaderAnimation
import com.alhorezmi.school.presentation.components.SectionTitle
import com.alhorezmi.school.presentation.localization.AppStrings

@Composable
fun LoaderScreen(strings: AppStrings) {
    BackgroundStage(background = Res.drawable.loader_background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimens.screenPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SectionTitle(strings.appTitle)
            Spacer(modifier = Modifier.height(AppDimens.largeSpacing))
            LoaderAnimation(modifier = Modifier.size(200.dp))
        }
    }
}
