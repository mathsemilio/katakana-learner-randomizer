package com.mathsemilio.katakanalearner.ui.screens.game.main.viewmodel

interface ViewModelRequestEventListener {
    fun onStartGameRequested(difficultyValue: Int)
    fun onCheckUserAnswerRequested(selectedRomanization: String)
    fun onGetNextSymbolRequested()
    fun onPauseGameTimerRequested()
    fun onResumeGameTimerRequested()
}
