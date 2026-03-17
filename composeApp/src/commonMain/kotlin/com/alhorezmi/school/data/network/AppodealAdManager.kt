package com.alhorezmi.school.data.network

/**
 * Abstract interface for Appodeal ad management.
 * Platform-specific implementations use expect/actual pattern.
 */
interface IAppodealAdManager {
    /**
     * Initialize Appodeal SDK with API key.
     * Should be called once at app startup.
     */
    fun initialize()

    /**
     * Load a banner ad.
     * Callback will be invoked when ad is ready or fails to load.
     */
    fun loadBannerAd(onAdReady: () -> Unit, onAdFailed: () -> Unit)

    /**
     * Check if banner ad is loaded and ready to show.
     */
    fun isBannerAdReady(): Boolean

    /**
     * Load a rewarded ad.
     * Callback will be invoked when ad is ready or fails to load.
     */
    fun loadRewardedAd(onAdReady: () -> Unit, onAdFailed: () -> Unit)

    /**
     * Check if rewarded ad is loaded and ready to show.
     */
    fun isRewardedAdReady(): Boolean

    /**
     * Show rewarded ad and invoke callbacks based on user action.
     * @param onRewarded Called when user watches ad completely and earns reward
     * @param onClosed Called when user closes ad (with or without reward)
     * @param onFailed Called if ad fails to show
     */
    fun showRewardedAd(
        onRewarded: () -> Unit,
        onClosed: () -> Unit,
        onFailed: () -> Unit,
    )
}

/**
 * Expect declaration for Android-specific implementation.
 * iOS build will ignore this due to commonMain/androidMain separation.
 */
expect class AppodealAdManager() : IAppodealAdManager
