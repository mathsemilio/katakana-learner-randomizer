package com.mathsemilio.katakanalearner.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.databinding.GameScoreScreenBinding

private const val PERFECT_SCORE = 48

/**
 * Fragment class for the game score screen
 */
class GameScoreScreen : Fragment() {

    //==========================================================================================
    // onCreateView
    //==========================================================================================
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating the layout with the inflate function from the fragment's binding class
        val binding: GameScoreScreenBinding =
            GameScoreScreenBinding.inflate(inflater, container, false)

        val gameScore = retrieveGameScore()

        /*
        Checking if the game score is equal to 48 (a perfect score), if it is, a different
        string for the textBodyYouScored TextView will be shown
        */
        if (gameScore == PERFECT_SCORE) {
            binding.textBodyYouScored.text = getString(R.string.perfect_score)
        }

        binding.textHeadlineScoreNumber.text = gameScore.toString()

        binding.buttonFinishGame.setOnClickListener {
            findNavController().navigate(R.id.action_gameScoreScreen_to_gameWelcomeScreen)
        }

        /*
        Checking if the game score equals 0, if it is, the Share button will be hidden, else,
        a listener will be attached.
        */
        if (gameScore == 0) {
            binding.textButtonShare.visibility = View.INVISIBLE
        } else {
            binding.textButtonShare.setOnClickListener { shareGameScore(gameScore) }
        }

        // Returning the root of the inflated layout
        return binding.root
    }

    //==========================================================================================
    // retrieveGameScore function
    //==========================================================================================
    /**
     * Function that returns the game score value from the argument bundle.
     */
    private fun retrieveGameScore(): Int {
        return GameScoreScreenArgs.fromBundle(requireArguments()).gameScore
    }

    //==========================================================================================
    // shareGameScore function
    //==========================================================================================
    /**
     * Function that builds an intent chooser, to enabled a user to share his game score.
     */
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
            Intent.createChooser(sendIntent, getString(R.string.game_score_create_chooser_title))

        startActivity(shareIntent)
    }
}