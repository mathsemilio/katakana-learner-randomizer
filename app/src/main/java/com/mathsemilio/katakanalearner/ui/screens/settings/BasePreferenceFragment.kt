package com.mathsemilio.katakanalearner.ui.screens.settings

import androidx.preference.PreferenceFragmentCompat
import com.mathsemilio.katakanalearner.di.ControllerCompositionRoot
import com.mathsemilio.katakanalearner.others.KatakanaRandomizerApplication

abstract class BasePreferenceFragment : PreferenceFragmentCompat() {

    protected fun getCompositionRoot(): ControllerCompositionRoot {
        return ControllerCompositionRoot(
            (requireActivity().application as KatakanaRandomizerApplication).compositionRoot,
            fragment = this
        )
    }
}
