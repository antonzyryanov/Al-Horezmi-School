package com.alhorezmi.school.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alhorezmi.school.app_design.AppDimens
import com.alhorezmi.school.app_design.CardSand
import com.alhorezmi.school.app_design.WarmPaper
import com.alhorezmi.school.data.network.IAppodealAdManager

/**
 * Composable banner ad container for displaying Appodeal banner ads.
 * Follows app design system with WarmPaper background and proper spacing.
 *
 * Banner is displayed at standard 320x50 or 728x90 height depending on device.
 * Shows loading indicator while ad is being fetched.
 *
 * @param adManager Instance of AppodealAdManager for ad lifecycle management
 */
@Composable
fun AppodealBannerAd(
    adManager: IAppodealAdManager,
    modifier: Modifier = Modifier,
) {
    val isAdLoaded = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        adManager.loadBannerAd(
            onAdReady = {
                isAdLoaded.value = true
                isLoading.value = false
            },
            onAdFailed = {
                isAdLoaded.value = false
                isLoading.value = false
            },
        )
    }

    if (!isLoading.value && !isAdLoaded.value) {
        // Ad failed to load, show nothing to minimize disruption
        return
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(AppDimens.buttonHeight)
            .background(WarmPaper),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading.value) {
            LoaderAnimation(modifier = Modifier.size(24.dp))
        } else if (isAdLoaded.value) {
            // Native ad view would be composed here in real implementation
            // For Kotlin Multiplatform, actual ad rendering happens in platform-specific code
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimens.buttonHeight)
                    .background(CardSand),
            )
        }
    }
}
