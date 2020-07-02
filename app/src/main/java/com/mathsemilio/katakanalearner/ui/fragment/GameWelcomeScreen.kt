package com.mathsemilio.katakanalearner.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.databinding.GameWelcomeScreenBinding
import com.mathsemilio.katakanalearner.util.DarkModeUtil

/**
 * Fragment class for the game's welcome screen.
 */
class GameWelcomeScreen : Fragment() {

    //==========================================================================================
    // onCreateView
    //==========================================================================================
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating the layout with the inflate function from the fragment's binding class
        val binding: GameWelcomeScreenBinding =
            GameWelcomeScreenBinding.inflate(inflater, container, false)

        // Listener for the buttonStart Button
        binding.buttonStart.setOnClickListener {
            findNavController().navigate(R.id.action_gameWelcomeScreen_to_mainGameScreen)
        }

        // Listener for the darkModeSwitchIcon ImageView
        binding.darkModeSwitchIcon.setOnClickListener { DarkModeUtil.switchDarkModeState() }

        // Returning the root of the inflated layout
        return binding.root
    }
}