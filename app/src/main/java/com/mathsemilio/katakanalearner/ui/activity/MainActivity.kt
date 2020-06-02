package com.mathsemilio.katakanalearner.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.mathsemilio.katakanalearner.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var darkModeActivated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image_dark_mode_switch.setOnClickListener { switchDarkMode(darkModeActivated) }
    }

    private fun switchDarkMode(activated: Boolean) {
        darkModeActivated = if (activated) {
            AppCompatDelegate.MODE_NIGHT_NO
            false
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
            true
        }
    }
}