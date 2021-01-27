package com.mathsemilio.katakanalearner.ui.screens.game.main.viewmodel

import com.mathsemilio.katakanalearner.domain.katakana.KatakanaSymbol

interface ViewModelEventListener {
    fun onGameScoreUpdated(newScore: Int)
    fun onGameProgressUpdated(updatedProgress: Int)
    fun onGameCountDownTimeUpdated(updatedCountdownTime: Int)
    fun onRomanizationGroupUpdated(updatedRomanizationGroupList: List<String>)
    fun onCurrentKatakanaSymbolUpdated(newSymbol: KatakanaSymbol)
    fun onCorrectAnswer()
    fun onWrongAnswer()
    fun onGameTimeOver()
}
