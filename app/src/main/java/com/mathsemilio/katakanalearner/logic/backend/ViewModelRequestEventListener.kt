package com.mathsemilio.katakanalearner.logic.backend

interface ViewModelRequestEventListener {
    fun onStartGameRequested(difficultyValue: Int)
    fun onCheckUserAnswerRequested(selectedRomanization: String)
    fun onGetNextSymbolRequested()
    fun onPauseGameTimerRequested()
    fun onResumeGameTimerRequested()
}
