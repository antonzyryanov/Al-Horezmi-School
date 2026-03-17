package com.alhorezmi.school.data.network

import com.alhorezmi.school.AndroidActivityHolder
import com.alhorezmi.school.BuildConfig
import com.appodeal.ads.Appodeal
import com.appodeal.ads.BannerCallbacks
import com.appodeal.ads.RewardedVideoCallbacks

actual class AppodealAdManager : IAppodealAdManager {
    private var initialized = false
    private var bannerReady = false
    private var rewardedReady = false

    private var bannerReadyCallback: (() -> Unit)? = null
    private var bannerFailCallback: (() -> Unit)? = null
    private var rewardedReadyCallback: (() -> Unit)? = null
    private var rewardedFailCallback: (() -> Unit)? = null

    private var rewardedCallback: (() -> Unit)? = null
    private var closedCallback: (() -> Unit)? = null
    private var showFailedCallback: (() -> Unit)? = null

    private companion object {
        val AD_TYPES = Appodeal.BANNER or Appodeal.REWARDED_VIDEO
    }

    override fun initialize() {
        if (initialized) return
        val activity = AndroidActivityHolder.currentActivity ?: return

        try {
            // Register callbacks before calling initialize so early callbacks are captured
            Appodeal.setTesting(true)
            setupCallbacks()
            Appodeal.initialize(activity, BuildConfig.APPODEAL_APP_KEY, AD_TYPES)
            initialized = true
        } catch (_: Throwable) {
            // Ad SDK failed to start; app continues without ads
        }
    }

    override fun loadBannerAd(onAdReady: () -> Unit, onAdFailed: () -> Unit) {
        initialize()
        val activity = AndroidActivityHolder.currentActivity
        if (activity == null || !initialized) {
            bannerReady = false
            onAdFailed()
            return
        }

        bannerReadyCallback = onAdReady
        bannerFailCallback = onAdFailed

        try {
            if (Appodeal.isLoaded(Appodeal.BANNER)) {
                bannerReady = true
                activity.runOnUiThread { Appodeal.show(activity, Appodeal.BANNER_BOTTOM) }
                bannerReadyCallback?.invoke()
                bannerReadyCallback = null
                bannerFailCallback = null
                return
            }
            Appodeal.cache(activity, Appodeal.BANNER)
        } catch (_: Throwable) {
            bannerReady = false
            bannerFailCallback?.invoke()
            bannerReadyCallback = null
            bannerFailCallback = null
        }
    }

    override fun isBannerAdReady(): Boolean = bannerReady

    override fun loadRewardedAd(onAdReady: () -> Unit, onAdFailed: () -> Unit) {
        initialize()
        val activity = AndroidActivityHolder.currentActivity
        if (activity == null || !initialized) {
            rewardedReady = false
            onAdFailed()
            return
        }

        rewardedReadyCallback = onAdReady
        rewardedFailCallback = onAdFailed

        try {
            if (Appodeal.isLoaded(Appodeal.REWARDED_VIDEO)) {
                rewardedReady = true
                rewardedReadyCallback?.invoke()
                rewardedReadyCallback = null
                rewardedFailCallback = null
                return
            }
            Appodeal.cache(activity, Appodeal.REWARDED_VIDEO)
        } catch (_: Throwable) {
            rewardedReady = false
            rewardedFailCallback?.invoke()
            rewardedReadyCallback = null
            rewardedFailCallback = null
        }
    }

    override fun isRewardedAdReady(): Boolean = rewardedReady

    override fun showRewardedAd(
        onRewarded: () -> Unit,
        onClosed: () -> Unit,
        onFailed: () -> Unit,
    ) {
        initialize()
        val activity = AndroidActivityHolder.currentActivity
        if (activity == null) {
            onFailed()
            return
        }

        rewardedCallback = onRewarded
        closedCallback = onClosed
        showFailedCallback = onFailed

        try {
            if (Appodeal.isLoaded(Appodeal.REWARDED_VIDEO)) {
                Appodeal.show(activity, Appodeal.REWARDED_VIDEO)
            } else {
                rewardedReady = false
                onFailed()
            }
        } catch (_: Throwable) {
            rewardedReady = false
            onFailed()
        }
    }

    private fun setupCallbacks() {
        Appodeal.setBannerCallbacks(object : BannerCallbacks {
            override fun onBannerLoaded(height: Int, isPrecache: Boolean) {
                bannerReady = true
                val activity = AndroidActivityHolder.currentActivity
                activity?.runOnUiThread {
                    try { Appodeal.show(activity, Appodeal.BANNER_BOTTOM) } catch (_: Throwable) {}
                }
                bannerReadyCallback?.invoke()
                bannerReadyCallback = null
                bannerFailCallback = null
            }

            override fun onBannerFailedToLoad() {
                bannerReady = false
                bannerFailCallback?.invoke()
                bannerReadyCallback = null
                bannerFailCallback = null
            }

            override fun onBannerShown() = Unit

            override fun onBannerShowFailed() {
                bannerReady = false
                bannerFailCallback?.invoke()
                bannerReadyCallback = null
                bannerFailCallback = null
            }

            override fun onBannerClicked() = Unit

            override fun onBannerExpired() {
                bannerReady = false
            }
        })

        Appodeal.setRewardedVideoCallbacks(object : RewardedVideoCallbacks {
            override fun onRewardedVideoLoaded(isPrecache: Boolean) {
                rewardedReady = true
                rewardedReadyCallback?.invoke()
                rewardedReadyCallback = null
                rewardedFailCallback = null
            }

            override fun onRewardedVideoFailedToLoad() {
                rewardedReady = false
                rewardedFailCallback?.invoke()
                rewardedReadyCallback = null
                rewardedFailCallback = null
            }

            override fun onRewardedVideoShown() = Unit

            override fun onRewardedVideoShowFailed() {
                rewardedReady = false
                showFailedCallback?.invoke()
            }

            override fun onRewardedVideoClicked() = Unit

            override fun onRewardedVideoFinished(amount: Double, currency: String?) {
                rewardedCallback?.invoke()
            }

            override fun onRewardedVideoClosed(finished: Boolean) {
                closedCallback?.invoke()
            }

            override fun onRewardedVideoExpired() {
                rewardedReady = false
            }
        })
    }
}
