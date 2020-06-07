package com.mathsemilio.katakanalearner.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.databinding.GameScoreScreenBinding

private const val TAG_SCORE_SCREEN = "GameScoreScreen"

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

        val gameScore = retrieveGameScore().also {
            Log.d(TAG_SCORE_SCREEN, "onCreateView: Game score value retrieved: $it")
        }

        binding.textHeadlineScoreNumber.text = gameScore.toString()

        binding.buttonFinishGame.setOnClickListener { navigateToWelcomeScreen() }

        /*
        Checking if the game score equals 0, if it is, the Share button will be hidden, else,
        a listener will be attached.
        */
        if (gameScore == 0) {
            Log.d(
                TAG_SCORE_SCREEN,
                "onCreateView: Game score value equals 0, hiding the share button"
            )
            binding.textButtonShare.visibility = View.GONE
        } else {
            Log.d(TAG_SCORE_SCREEN, "onCreateView: Game score value > 0, showing the share button")
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
            Intent.createChooser(sendIntent, getString(R.string.share_intent_chooser_title))

        Log.i(TAG_SCORE_SCREEN, "shareGameScore: Starting the intent activity")
        startActivity(shareIntent)
    }

    //==========================================================================================
    // navigateToWelcomeScreen function
    //==========================================================================================
    /**
     * Function to navigate from the current screen to the welcome screen.
     */
    private fun navigateToWelcomeScreen() {
        activity?.findNavController(R.id.nav_host_fragment)
            ?.navigate(R.id.action_gameScoreScreen_to_gameWelcomeScreen)
    }
}