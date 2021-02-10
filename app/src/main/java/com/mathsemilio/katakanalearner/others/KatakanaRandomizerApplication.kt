package com.mathsemilio.katakanalearner.others

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.mathsemilio.katakanalearner.commom.dependencyinjection.CompositionRoot

class KatakanaRandomizerApplication : Application() {

    private lateinit var _compositionRoot: CompositionRoot
    val compositionRoot get() = _compositionRoot

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        _compositionRoot = CompositionRoot()
        compositionRoot.getAppThemeUtil(applicationContext).setAppThemeFromPreferenceValue()
    }
}