package com.mathsemilio.katakanalearner.ui.screens

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.google.android.material.appbar.MaterialToolbar
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.ui.others.FragmentContainerHelper
import com.mathsemilio.katakanalearner.ui.others.ToolbarVisibilityHelper
import com.mathsemilio.katakanalearner.ui.screens.game.welcome.GameWelcomeScreen

class MainActivity : AppCompatActivity(), FragmentContainerHelper, ToolbarVisibilityHelper {

    private lateinit var mAppToolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_KatakanaLearner)
        setContentView(R.layout.activity_main)

        setupAppToolbar()

        if (savedInstanceState == null) showGameWelcomeScreenFragment()
    }

    private fun setupAppToolbar() {
        mAppToolbar = findViewById(R.id.material_toolbar_app)
        mAppToolbar.apply {
            title = getString(R.string.settings_toolbar_title)
            navigationIcon = ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_baseline_arrow_back_24,
                null
            )
            setNavigationOnClickListener { supportFragmentManager.popBackStackImmediate() }
            isVisible = false
        }
    }

    private fun showGameWelcomeScreenFragment() {
        supportFragmentManager.beginTransaction().apply {
            add(getFragmentContainer().id, GameWelcomeScreen())
            commitNow()
        }
    }

    override fun getFragmentContainer(): FrameLayout =
        findViewById(R.id.frame_layout_fragment_container)

    override fun setToolbarVisibility(isVisible: Boolean) {
        mAppToolbar.isVisible = isVisible
    }
}