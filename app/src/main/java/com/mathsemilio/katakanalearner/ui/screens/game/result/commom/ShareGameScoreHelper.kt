package com.mathsemilio.katakanalearner.ui.screens.game.result.commom

import android.content.Context
import android.content.Intent
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.commom.PERFECT_SCORE

class ShareGameScoreHelper(private val context: Context) {

    private fun getShareGameScoreIntent(score: Int): Intent {
        return Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getShareGameScoreString(score))
            type = "text/plain"
        }
    }

    private fun getShareGameScoreString(score: Int): String {
        return if (score == PERFECT_SCORE) {
            context.getString(R.string.share_perfect_final_score)
        } else {
            context.getString(R.string.share_final_score, score)
        }
    }

    fun shareGameScore(score: Int) {
        context.apply {
            startActivity(
                Intent.createChooser(
                    getShareGameScoreIntent(score),
                    getString(R.string.game_score_create_chooser_title)
                )
            )
        }
    }
}