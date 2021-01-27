package com.mathsemilio.katakanalearner.ui.screens.commom

import androidx.fragment.app.Fragment
import com.mathsemilio.katakanalearner.di.ControllerCompositionRoot
import com.mathsemilio.katakanalearner.others.KatakanaRandomizerApplication

abstract class BaseFragment : Fragment() {

    protected fun getCompositionRoot(): ControllerCompositionRoot {
        return ControllerCompositionRoot(
            (requireActivity().application as KatakanaRandomizerApplication).compositionRoot,
            fragment = this
        )
    }
}