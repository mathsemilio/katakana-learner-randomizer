package com.mathsemilio.katakanalearner.ui.screens.commom

import androidx.fragment.app.Fragment
import com.mathsemilio.katakanalearner.commom.dependencyinjection.ControllerCompositionRoot
import com.mathsemilio.katakanalearner.ui.screens.MainActivity

abstract class BaseFragment : Fragment() {

    private val _compositionRoot by lazy {
        ControllerCompositionRoot((requireActivity() as MainActivity).activityCompositionRoot)
    }
    val compositionRoot get() = _compositionRoot
}