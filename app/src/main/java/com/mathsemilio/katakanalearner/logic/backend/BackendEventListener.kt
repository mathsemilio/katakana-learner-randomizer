package com.mathsemilio.katakanalearner.logic.backend

import com.mathsemilio.katakanalearner.domain.katakana.KatakanaSymbol

interface BackendEventListener {
    fun onSymbolUpdated(newSymbol: KatakanaSymbol)
    fun onGameScoreUpdated(newScore: Int)
    fun onGameProgressUpdated(updatedProgress: Int)
    fun onGameCountdownTimeUpdated(updatedCountdownTime: Int)
    fun onRomanizationGroupUpdated(updatedRomanizationGroupList: List<String>)
    fun onCorrectAnswer()
    fun onWrongAnswer()
    fun onGameTimeOver()
    fun onGameFinished()
}
