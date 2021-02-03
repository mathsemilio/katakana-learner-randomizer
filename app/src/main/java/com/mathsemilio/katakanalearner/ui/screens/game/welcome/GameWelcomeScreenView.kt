package com.mathsemilio.katakanalearner.ui.screens.game.welcome

interface GameWelcomeScreenView {
    interface Listener {
        fun onPlayClickSoundEffect()
        fun onSettingsIconClicked()
        fun onStartButtonClicked(difficultyValue: Int)
    }

    fun setupUI(difficultyValueFromPreference: String)
    fun getDifficultyValue(): Int
}