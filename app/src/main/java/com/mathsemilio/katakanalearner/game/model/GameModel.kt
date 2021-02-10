package com.mathsemilio.katakanalearner.game.model

import com.mathsemilio.katakanalearner.commom.baseobservable.BaseObservable
import com.mathsemilio.katakanalearner.domain.entity.katakana.KatakanaSymbol
import com.mathsemilio.katakanalearner.game.backend.GameBackend

class GameModel : BaseObservable<GameModel.Listener>(), GameBackend.Listener {

    interface Listener {
        fun onGameScoreUpdated(newScore: Int)
        fun onGameProgressUpdated(updatedProgress: Int)
        fun onGameCountDownTimeUpdated(updatedCountdownTime: Int)
        fun onRomanizationGroupUpdated(updatedRomanizationGroupList: List<String>)
        fun onCurrentKatakanaSymbolUpdated(newSymbol: KatakanaSymbol)
        fun onCorrectAnswer()
        fun onWrongAnswer()
        fun onGameTimeOver()
    }

    private val gameBackend = GameBackend()
    private val viewModelRequest = gameBackend as ModelRequestEventListener

    private lateinit var _currentKatakanaSymbol: KatakanaSymbol
    val currentKatakanaSymbol get() = _currentKatakanaSymbol

    private var _currentGameScore = 0
    val currentGameScore get() = _currentGameScore

    private var _gameFinished = false
    val gameFinished get() = _gameFinished

    init {
        gameBackend.addListener(this)
    }

    fun startGame(difficultyValue: Int) {
        viewModelRequest.onStartGameRequested(difficultyValue)
    }

    fun checkUserAnswer(selectedRomanization: String) {
        viewModelRequest.onCheckUserAnswerRequested(selectedRomanization)
    }

    fun getNextSymbol() {
        viewModelRequest.onGetNextSymbolRequested()
    }

    fun pauseGameTimer() {
        viewModelRequest.onPauseGameTimerRequested()
    }

    fun resumeGameTimer() {
        viewModelRequest.onResumeGameTimerRequested()
    }

    fun onClearInstance() {
        gameBackend.removeListener(this)
    }

    override fun onSymbolUpdated(newSymbol: KatakanaSymbol) {
        _currentKatakanaSymbol = newSymbol
        listeners.forEach { it.onCurrentKatakanaSymbolUpdated(newSymbol) }
    }

    override fun onGameScoreUpdated(newScore: Int) {
        _currentGameScore = newScore
        listeners.forEach { it.onGameScoreUpdated(newScore) }
    }

    override fun onGameProgressUpdated(updatedProgress: Int) {
        listeners.forEach { it.onGameProgressUpdated(updatedProgress) }
    }

    override fun onGameCountdownTimeUpdated(updatedCountdownTime: Int) {
        listeners.forEach { it.onGameCountDownTimeUpdated(updatedCountdownTime) }
    }

    override fun onRomanizationGroupUpdated(updatedRomanizationGroupList: List<String>) {
        listeners.forEach { it.onRomanizationGroupUpdated(updatedRomanizationGroupList) }
    }

    override fun onCorrectAnswer() {
        listeners.forEach { it.onCorrectAnswer() }
    }

    override fun onWrongAnswer() {
        listeners.forEach { it.onWrongAnswer() }
    }

    override fun onGameTimeOver() {
        listeners.forEach { it.onGameTimeOver() }
    }

    override fun onGameFinished() {
        _gameFinished = true
    }
}