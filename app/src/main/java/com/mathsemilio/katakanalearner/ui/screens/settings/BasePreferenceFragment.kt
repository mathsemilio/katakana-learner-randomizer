package com.mathsemilio.katakanalearner.ui.screens.settings

import androidx.preference.PreferenceFragmentCompat
import com.mathsemilio.katakanalearner.commom.dependencyinjection.ControllerCompositionRoot
import com.mathsemilio.katakanalearner.ui.screens.MainActivity

abstract class BasePreferenceFragment : PreferenceFragmentCompat() {

    private val _compositionRoot by lazy {
        ControllerCompositionRoot((requireActivity() as MainActivity).activityCompositionRoot)
    }
    val compositionRoot get() = _compositionRoot
}
