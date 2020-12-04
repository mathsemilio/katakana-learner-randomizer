package com.mathsemilio.katakanalearner

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.mathsemilio.katakanalearner.ui.commom.util.AppThemeUtil

@Suppress("unused")
class KatakanaRandomizerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        AppThemeUtil(applicationContext).setAppThemeFromPreferenceValue()
    }
}