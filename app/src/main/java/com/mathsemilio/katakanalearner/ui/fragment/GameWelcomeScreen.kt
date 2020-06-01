package com.mathsemilio.katakanalearner.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.databinding.GameWelcomeScreenBinding

class GameWelcomeScreen : Fragment() {

    private lateinit var binding: GameWelcomeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GameWelcomeScreenBinding.inflate(inflater, container, false)

        binding.buttonPlay.setOnClickListener { navigateToMainScreen() }

        return binding.root
    }

    private fun navigateToMainScreen() {
        activity?.findNavController(R.id.nav_host_fragment)
            ?.navigate(R.id.action_gameWelcomeScreen_to_mainGameScreen)
    }
}