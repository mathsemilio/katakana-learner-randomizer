package com.mathsemilio.katakanalearner.ui.commom

import androidx.fragment.app.Fragment
import com.mathsemilio.katakanalearner.KatakanaRandomizerApplication
import com.mathsemilio.katakanalearner.di.CompositionRoot

abstract class BaseFragment : Fragment() {
    fun getCompositionRoot(): CompositionRoot {
        return (requireActivity().application as KatakanaRandomizerApplication).compositionRoot
    }
}