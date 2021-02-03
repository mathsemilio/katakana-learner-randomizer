package com.mathsemilio.katakanalearner.ui.screens

import androidx.appcompat.app.AppCompatActivity
import com.mathsemilio.katakanalearner.di.ActivityCompositionRoot
import com.mathsemilio.katakanalearner.others.KatakanaRandomizerApplication

abstract class BaseActivity : AppCompatActivity() {

    private val _activityCompositionRoot by lazy {
        ActivityCompositionRoot(
            (application as KatakanaRandomizerApplication).compositionRoot,
            activity = this
        )
    }
    val activityCompositionRoot get() = _activityCompositionRoot
}