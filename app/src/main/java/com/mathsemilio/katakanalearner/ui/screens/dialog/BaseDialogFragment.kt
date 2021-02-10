package com.mathsemilio.katakanalearner.ui.screens.dialog

import androidx.fragment.app.DialogFragment
import com.mathsemilio.katakanalearner.commom.dependencyinjection.ControllerCompositionRoot
import com.mathsemilio.katakanalearner.ui.screens.MainActivity

abstract class BaseDialogFragment : DialogFragment() {

    private val _compositionRoot by lazy {
        ControllerCompositionRoot((requireActivity() as MainActivity).activityCompositionRoot)
    }
    val compositionRoot get() = _compositionRoot
}