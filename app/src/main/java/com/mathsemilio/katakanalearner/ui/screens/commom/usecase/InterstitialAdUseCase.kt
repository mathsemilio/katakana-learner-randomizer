package com.mathsemilio.katakanalearner.ui.screens.commom.usecase

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.commom.observable.BaseObservable

class InterstitialAdUseCase(
    private val activity: FragmentActivity,
    private val context: Context,
    private val adRequest: AdRequest,
) : BaseObservable<InterstitialAdUseCase.Listener>() {

    interface Listener {
        fun onAdDismissed()
        fun onAdFailedToShow()
    }

    private var useCaseInterstitialAd: InterstitialAd? = null

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
                    useCaseInterstitialAd = interstitialAd
                    useCaseInterstitialAd?.fullScreenContentCallback = getFullScreenContentCallback()
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    useCaseInterstitialAd = null
                }
            }
        )
    }

    private fun getFullScreenContentCallback(): FullScreenContentCallback {
        return object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                onAdFailedToShow()
            }

            override fun onAdDismissedFullScreenContent() {
                onAdDismissed()
            }
        }
    }

    fun showInterstitialAd() {
        if (useCaseInterstitialAd == null)
            onAdFailedToShow()
        else
            useCaseInterstitialAd?.show(activity)
    }

    private fun onAdFailedToShow() {
        getListeners().forEach { it.onAdFailedToShow() }
    }

    private fun onAdDismissed() {
        getListeners().forEach { it.onAdDismissed() }
    }
}