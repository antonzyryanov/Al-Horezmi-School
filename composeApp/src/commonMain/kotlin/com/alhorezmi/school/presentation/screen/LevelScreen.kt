package com.alhorezmi.school.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alhorezmi.school.app_design.AppDimens
import com.alhorezmi.school.app_design.InkBlack
import com.alhorezmi.school.data.network.IAppodealAdManager
import com.alhorezmi.school.generated.resources.Res
import com.alhorezmi.school.generated.resources.level_background
import com.alhorezmi.school.presentation.components.AnswerOptionRow
import com.alhorezmi.school.presentation.components.AppodealRewardedAdDialog
import com.alhorezmi.school.presentation.components.BackgroundStage
import com.alhorezmi.school.presentation.components.PrimaryActionButton
import com.alhorezmi.school.presentation.components.ScoreBadge
import com.alhorezmi.school.presentation.components.ScreenCard
import com.alhorezmi.school.presentation.localization.AppStrings
import com.alhorezmi.school.presentation.viewmodel.LevelUiState

@Composable
fun LevelScreen(
    strings: AppStrings,
    uiState: LevelUiState,
    adManager: IAppodealAdManager,
    showRewardedAd: Boolean,
    onRewardedAdClosed: () -> Unit,
    onAnswerSelected: (String) -> Unit,
    onBackPressed: () -> Unit = {},
) {
    if (showRewardedAd) {
        AppodealRewardedAdDialog(
            adManager = adManager,
            strings = strings,
            onClosed = onRewardedAdClosed,
        )
        return
    }

    val question = uiState.currentQuestion ?: return
    val answers = listOf(question.answer_1, question.answer_2, question.answer_3, question.answer_4)

    BackgroundStage(background = Res.drawable.level_background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimens.screenPadding),
            verticalArrangement = Arrangement.spacedBy(AppDimens.contentSpacing),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimens.buttonHeight),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PrimaryActionButton(
                    text = "← ${strings.backToMenu}",
                    modifier = Modifier.weight(1f),
                    onClick = onBackPressed,
                )
                Spacer(modifier = Modifier.width(AppDimens.contentSpacing))
                ScoreBadge(
                    scoreLabel = strings.score,
                    scoreValue = uiState.playerProfile.score,
                    modifier = Modifier
                        .weight(1f)
                        .height(AppDimens.buttonHeight),
                )
            }

            ScreenCard(modifier = Modifier.fillMaxWidth()) {
                Text(text = uiState.themeName, color = InkBlack)
                Text(
                    text = "${strings.questionProgress} ${uiState.questionIndex + 1}/${uiState.totalQuestions}",
                    color = InkBlack,
                )
                Text(
                    text = question.answer,
                    color = InkBlack,
                    style = MaterialTheme.typography.headlineMedium,
                )
                answers.forEach { answer ->
                    AnswerOptionRow(title = answer) {
                        onAnswerSelected(answer)
                    }
                }
            }
        }
    }
}
