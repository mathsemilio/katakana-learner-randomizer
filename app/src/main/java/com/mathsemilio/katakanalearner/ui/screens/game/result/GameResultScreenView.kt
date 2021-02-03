package com.mathsemilio.katakanalearner.ui.screens.game.result

import com.google.android.gms.ads.AdRequest

interface GameResultScreenView {
    interface Listener {
        fun onHomeButtonClicked()
        fun onPlayAgainClicked(difficultyValue: Int)
        fun onShareScoreButtonClicked()
    }

    fun setupUI(difficultyValue: Int, score: Int, perfectScores: Int)
    fun loadBannerAd(adRequest: AdRequest)
}