package com.mathsemilio.katakanalearner.ui.others

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.commom.baseobservable.BaseObservable

class InterstitialAdHelper(
    private val activity: FragmentActivity,
    private val context: Context,
    private val adRequest: AdRequest,
) : BaseObservable<InterstitialAdHelper.Listener>() {

    interface Listener {
        fun onAdDismissed()
        fun onAdFailedToLoad()
    }

    private var mInterstitialAd: InterstitialAd? = null

    init {
        initializeInterstitialAd()
    }

    private fun initializeInterstitialAd() {
        InterstitialAd.load(
            context,
            context.getString(R.string.interstitialAdTestUnitId),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.fullScreenContentCallback = getFullScreenContentCallback()
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }
            }
        )
    }

    private fun getFullScreenContentCallback(): FullScreenContentCallback {
        return object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(adError: AdError?) = onAdFailedToLoad()
            override fun onAdDismissedFullScreenContent() = onAdDismissed()
        }
    }

    fun showInterstitialAd() {
        if (mInterstitialAd == null)
            onAdFailedToLoad()
        else
            mInterstitialAd?.show(activity)
    }

    private fun onAdFailedToLoad() {
        listeners.forEach { it.onAdFailedToLoad() }
    }

    private fun onAdDismissed() {
        listeners.forEach { it.onAdDismissed() }
    }
}