package com.alhorezmi.school.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.alhorezmi.school.app_design.AppDimens
import com.alhorezmi.school.app_design.InkBlack
import com.alhorezmi.school.domain.model.QuizResult
import com.alhorezmi.school.generated.resources.Res
import com.alhorezmi.school.generated.resources.loader_background
import com.alhorezmi.school.presentation.components.BackgroundStage
import com.alhorezmi.school.presentation.components.PrimaryActionButton
import com.alhorezmi.school.presentation.components.ScreenCard
import com.alhorezmi.school.presentation.components.SectionTitle
import com.alhorezmi.school.presentation.localization.AppStrings

@Composable
fun ResultsScreen(
    strings: AppStrings,
    result: QuizResult,
    onBackToMenu: () -> Unit,
) {
    val quote = when {
        result.score < 25 -> strings.quoteLow
        result.score < 40 -> strings.quoteMedium
        else -> strings.quoteHigh
    }
    BackgroundStage(background = Res.drawable.loader_background) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimens.screenPadding),
            contentAlignment = Alignment.Center,
        ) {
            ScreenCard(modifier = Modifier.fillMaxWidth()) {
                SectionTitle(result.themeName)
                Text(
                    text = "${strings.yourScore}: ${result.score}/50",
                    color = InkBlack,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(
                    text = quote,
                    color = InkBlack,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                PrimaryActionButton(
                    text = strings.backToMenu,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onBackToMenu,
                )
            }
        }
    }
}
