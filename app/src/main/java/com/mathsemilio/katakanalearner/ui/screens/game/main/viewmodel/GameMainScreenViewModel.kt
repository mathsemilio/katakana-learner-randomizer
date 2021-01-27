package com.mathsemilio.katakanalearner.ui.screens.game.main.viewmodel

import com.mathsemilio.katakanalearner.commom.BaseObservable
import com.mathsemilio.katakanalearner.domain.katakana.KatakanaSymbol
import com.mathsemilio.katakanalearner.logic.backend.BackendEventListener
import com.mathsemilio.katakanalearner.logic.backend.GameBackend
import com.mathsemilio.katakanalearner.logic.backend.ViewModelRequestEventListener

class GameMainScreenViewModel : BaseObservable<ViewModelEventListener>(), BackendEventListener {

    private val mGameBackend = GameBackend()
    private val mViewModelRequest = mGameBackend as ViewModelRequestEventListener

    private lateinit var mCurrentKatakanaSymbol: KatakanaSymbol
    private var mCurrentGameScore = 0
    var gameFinished = false

    init {
        mGameBackend.registerListener(this)
    }

    fun startGame(difficultyValue: Int) {
        mViewModelRequest.onStartGameRequested(difficultyValue)
    }

    fun checkUserAnswer(selectedRomanization: String) {
        mViewModelRequest.onCheckUserAnswerRequested(selectedRomanization)
    }

    fun getNextSymbol() {
        mViewModelRequest.onGetNextSymbolRequested()
    }

    fun pauseGameTimer() {
        mViewModelRequest.onPauseGameTimerRequested()
    }

    fun resumeGameTimer() {
        mViewModelRequest.onResumeGameTimerRequested()
    }

    fun getCurrentSymbol(): KatakanaSymbol {
        return mCurrentKatakanaSymbol
    }

    fun getGameScore(): Int {
        return mCurrentGameScore
    }

    fun onClearInstance() {
        mGameBackend.removeListener(this)
    }

    override fun onSymbolUpdated(newSymbol: KatakanaSymbol) {
        mCurrentKatakanaSymbol = newSymbol
        getListeners().forEach { it.onCurrentKatakanaSymbolUpdated(newSymbol) }
    }

    override fun onGameScoreUpdated(newScore: Int) {
        mCurrentGameScore = newScore
        getListeners().forEach { it.onGameScoreUpdated(newScore) }
    }

    override fun onGameProgressUpdated(updatedProgress: Int) {
        getListeners().forEach { it.onGameProgressUpdated(updatedProgress) }
    }

    override fun onGameCountdownTimeUpdated(updatedCountdownTime: Int) {
        getListeners().forEach { it.onGameCountDownTimeUpdated(updatedCountdownTime) }
    }

    override fun onRomanizationGroupUpdated(updatedRomanizationGroupList: List<String>) {
        getListeners().forEach { it.onRomanizationGroupUpdated(updatedRomanizationGroupList) }
    }

    override fun onCorrectAnswer() {
        getListeners().forEach { it.onCorrectAnswer() }
    }

    override fun onWrongAnswer() {
        getListeners().forEach { it.onWrongAnswer() }
    }

    override fun onGameTimeOver() {
        getListeners().forEach { it.onGameTimeOver() }
    }

    override fun onGameFinished() {
        gameFinished = true
    }
}