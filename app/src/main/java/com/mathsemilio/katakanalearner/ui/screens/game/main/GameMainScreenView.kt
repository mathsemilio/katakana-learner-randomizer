package com.mathsemilio.katakanalearner.ui.screens.game.main

interface GameMainScreenView {
    interface Listener {
        fun playClickSoundEffect()
        fun onExitButtonClicked()
        fun onPauseButtonClicked()
        fun onCheckAnswerClicked(selectedRomanization: String)
    }

    fun onControllerViewCreated(difficultyValue: Int)
    fun setGameDifficultyTextBasedOnDifficultyValue(difficultyValue: Int)
    fun updateGameScoreTextView(newScore: Int)
    fun updateCurrentKatakanaSymbol(newSymbol: String)
    fun updateProgressBarGameTimerProgressValue(updatedCountdownTime: Int)
    fun updateRomanizationOptionsGroup(updatedRomanizationList: List<String>)
    fun updateProgressBarGameProgressValue(updatedProgress: Int)
    fun clearRomanizationOptions()
}