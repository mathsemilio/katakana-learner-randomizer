package com.mathsemilio.katakanalearner.ui.screens.dialog

import androidx.fragment.app.DialogFragment
import com.mathsemilio.katakanalearner.di.ControllerCompositionRoot
import com.mathsemilio.katakanalearner.others.KatakanaRandomizerApplication

abstract class BaseDialogFragment : DialogFragment() {

    protected fun getCompositionRoot(): ControllerCompositionRoot {
        return ControllerCompositionRoot(
            (requireActivity().application as KatakanaRandomizerApplication).compositionRoot,
            fragment = this
        )
    }
}