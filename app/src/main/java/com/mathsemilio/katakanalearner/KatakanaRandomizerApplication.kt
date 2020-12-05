package com.mathsemilio.katakanalearner

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.mathsemilio.katakanalearner.di.CompositionRoot

@Suppress("unused")
class KatakanaRandomizerApplication : Application() {

    private lateinit var _compositionRoot: CompositionRoot
    val compositionRoot get() = _compositionRoot

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        _compositionRoot = CompositionRoot()
        compositionRoot.getAppThemeUtil(this).setAppThemeFromPreferenceValue()
    }
}