package com.alhorezmi.school.data.network

/**
 * iOS stub implementation of Appodeal ad manager.
 * iOS implementation would require native Swift code and Appodeal iOS SDK.
 * For now, this is a no-op implementation that returns false for all ad states.
 */
actual class AppodealAdManager : IAppodealAdManager {

    override fun initialize() {
        // iOS implementation would initialize Appodeal SDK here
        // Would require Swift interop with Appodeal iOS SDK
    }

    override fun loadBannerAd(onAdReady: () -> Unit, onAdFailed: () -> Unit) {
        // Stub: Always fail on iOS for now
        onAdFailed()
    }

    override fun isBannerAdReady(): Boolean = false

    override fun loadRewardedAd(onAdReady: () -> Unit, onAdFailed: () -> Unit) {
        // Stub: Always fail on iOS for now
        onAdFailed()
    }

    override fun isRewardedAdReady(): Boolean = false

    override fun showRewardedAd(
        onRewarded: () -> Unit,
        onClosed: () -> Unit,
        onFailed: () -> Unit,
    ) {
        // Stub: Always fail on iOS for now
        onFailed()
    }
}
