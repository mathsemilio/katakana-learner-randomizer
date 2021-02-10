package com.mathsemilio.katakanalearner.game.model

interface ModelRequestEventListener {
    fun onStartGameRequested(difficultyValue: Int)
    fun onCheckUserAnswerRequested(selectedRomanization: String)
    fun onGetNextSymbolRequested()
    fun onPauseGameTimerRequested()
    fun onResumeGameTimerRequested()
}
