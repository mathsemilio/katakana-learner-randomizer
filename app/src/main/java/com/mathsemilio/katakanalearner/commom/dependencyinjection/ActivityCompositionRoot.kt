package com.mathsemilio.katakanalearner.commom.dependencyinjection

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.mathsemilio.katakanalearner.data.preferences.repository.PreferencesRepository
import com.mathsemilio.katakanalearner.others.SoundEffectsModule
import com.mathsemilio.katakanalearner.ui.others.FragmentContainerHelper
import com.mathsemilio.katakanalearner.ui.others.ScreensNavigator
import com.mathsemilio.katakanalearner.ui.others.ViewFactory

class ActivityCompositionRoot(
    private val compositionRoot: CompositionRoot,
    private val activity: AppCompatActivity
) {
    private val _preferencesRepository by lazy {
        PreferencesRepository(context)
    }

    private val _screensNavigator by lazy {
        ScreensNavigator(fragmentManager, activity as FragmentContainerHelper)
    }

    private val _viewFactory by lazy {
        ViewFactory(LayoutInflater.from(context))
    }

    fun getActivity() = activity

    val context get() = activity

    val fragmentManager get() = activity.supportFragmentManager

    val adRequest get() = compositionRoot.adRequest

    val appThemeUtil get() = compositionRoot.getAppThemeUtil(context)

    val preferencesRepository get() = _preferencesRepository

    val screensNavigator get() = _screensNavigator

    val soundEffectsModule
        get() = SoundEffectsModule(context, preferencesRepository.soundEffectsVolume)

    val viewFactory get() = _viewFactory
}