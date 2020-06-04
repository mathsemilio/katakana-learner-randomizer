package com.mathsemilio.katakanalearner.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.databinding.GameWelcomeScreenBinding
import com.mathsemilio.katakanalearner.util.DarkModeSelector

private const val TAG_WELCOME_SCREEN = "GameWelcomeScreen"

class GameWelcomeScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: GameWelcomeScreenBinding =
            GameWelcomeScreenBinding.inflate(inflater, container, false)

        binding.buttonStart.setOnClickListener { navigateToMainScreen() }

        binding.darkModeSwitch.isChecked = DarkModeSelector.isActivated

        binding.darkModeSwitch.setOnCheckedChangeListener { buttonView, _ ->
            if (buttonView.isChecked) {
                Log.i(TAG_WELCOME_SCREEN, "onCreateView: Dark mode on")
                DarkModeSelector.switchDarkModeState()
            } else {
                Log.i(TAG_WELCOME_SCREEN, "onCreateView: Dark mode off")
                DarkModeSelector.switchDarkModeState()
            }
        }

        return binding.root
    }

    private fun navigateToMainScreen() {
        activity?.findNavController(R.id.nav_host_fragment)
            ?.navigate(R.id.action_gameWelcomeScreen_to_mainGameScreen)
    }
}