package com.mathsemilio.katakanalearner.commom.dependencyinjection

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.mathsemilio.katakanalearner.ui.others.AppThemeUtil

class CompositionRoot {

    val adRequest: AdRequest
        get() = AdRequest.Builder().build()

    fun getAppThemeUtil(context: Context) = AppThemeUtil(context)
}