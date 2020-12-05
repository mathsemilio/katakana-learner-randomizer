package com.mathsemilio.katakanalearner.di

import android.content.Context
import android.media.SoundPool
import com.google.android.gms.ads.InterstitialAd
import com.mathsemilio.katakanalearner.data.preferences.repository.PreferencesRepository
import com.mathsemilio.katakanalearner.ui.commom.util.AppThemeUtil
import com.mathsemilio.katakanalearner.ui.commom.util.setupAndLoadInterstitialAd
import com.mathsemilio.katakanalearner.ui.commom.util.setupSoundPool

class CompositionRoot {

    fun getPreferencesRepository(context: Context): PreferencesRepository {
        return PreferencesRepository(context)
    }

    fun getAppThemeUtil(context: Context): AppThemeUtil {
        return AppThemeUtil(context)
    }

    fun getInterstitialAd(context: Context, onAdClosed: () -> Unit): InterstitialAd {
        return setupAndLoadInterstitialAd(context) { onAdClosed() }
    }

    fun getSoundPool(maxAudioStreams: Int): SoundPool = setupSoundPool(maxAudioStreams)
}