package com.mathsemilio.katakanalearner.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.databinding.GameScoreScreenBinding

class GameScoreScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: GameScoreScreenBinding =
            GameScoreScreenBinding.inflate(inflater, container, false)

        val gameScore = retrieveGameScore()

        binding.textHeadlineScoreNumber.text = gameScore.toString()

        binding.buttonFinishGame.setOnClickListener { navigateToWelcomeScreen() }

        if (gameScore == 0) {
            binding.textButtonShare.visibility = View.GONE
        } else {
            binding.textButtonShare.setOnClickListener { shareGameScore(gameScore) }
        }

        return binding.root
    }

    private fun retrieveGameScore(): Int {
        return GameScoreScreenArgs.fromBundle(requireArguments()).gameScore
    }

    private fun shareGameScore(gameScore: Int) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                resources.getQuantityString(R.plurals.game_score_plurals, gameScore, gameScore)
            )
            type = "text/plain"
        }

        val shareIntent =
            Intent.createChooser(sendIntent, getString(R.string.share_intent_chooser_title))

        startActivity(shareIntent)
    }

    private fun navigateToWelcomeScreen() {
        activity?.findNavController(R.id.nav_host_fragment)
            ?.navigate(R.id.action_gameScoreScreen_to_gameWelcomeScreen)
    }
}