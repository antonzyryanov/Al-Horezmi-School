package com.alhorezmi.school.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alhorezmi.school.app_design.AppDimens
import com.alhorezmi.school.app_design.InkBlack
import com.alhorezmi.school.app_design.OverlayScrim
import com.alhorezmi.school.data.network.IAppodealAdManager
import com.alhorezmi.school.generated.resources.Res
import com.alhorezmi.school.generated.resources.level_background
import com.alhorezmi.school.presentation.localization.AppStrings
import kotlinx.coroutines.delay

@Composable
fun AppodealRewardedAdDialog(
    adManager: IAppodealAdManager,
    strings: AppStrings,
    onClosed: () -> Unit,
) {
    val state = remember { mutableStateOf(RewardedGateState.Loading) }
    val rewarded = remember { mutableStateOf(false) }
    val closed = remember { mutableStateOf(false) }

    val closeGate: () -> Unit = {
        if (!closed.value) {
            closed.value = true
            onClosed()
        }
    }

    LaunchedEffect(Unit) {
        adManager.loadRewardedAd(
            onAdReady = { state.value = RewardedGateState.Ready },
            onAdFailed = {
                state.value = RewardedGateState.Failed
            },
        )
    }

    // Wait at most 3 seconds for the rewarded ad to become ready.
    // If still not ready, skip ad gate and open the level screen.
    LaunchedEffect(Unit) {
        delay(3000)
        if (state.value != RewardedGateState.Ready) {
            closeGate()
        }
    }

    if (state.value == RewardedGateState.Ready) {
        LaunchedEffect("show_rewarded") {
            adManager.showRewardedAd(
                onRewarded = { rewarded.value = true },
                onClosed = {
                    closeGate()
                },
                onFailed = {
                    state.value = RewardedGateState.Failed
                    closeGate()
                },
            )
        }
    }

    BackgroundStage(background = Res.drawable.level_background) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(OverlayScrim),
            contentAlignment = Alignment.Center,
        ) {
            ScreenCard {
                Column(
                    modifier = Modifier.padding(AppDimens.screenPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = strings.loading,
                        style = MaterialTheme.typography.headlineSmall,
                        color = InkBlack,
                    )
                    Spacer(modifier = Modifier.height(AppDimens.contentSpacing))
                    LoaderAnimation(modifier = Modifier.size(72.dp))
                }
            }
        }
    }
}

private enum class RewardedGateState {
    Loading,
    Ready,
    Failed,
}
