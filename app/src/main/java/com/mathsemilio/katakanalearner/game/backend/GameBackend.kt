package com.mathsemilio.katakanalearner.game.backend

import android.os.CountDownTimer
import com.mathsemilio.katakanalearner.commom.*
import com.mathsemilio.katakanalearner.commom.baseobservable.BaseObservable
import com.mathsemilio.katakanalearner.domain.entity.katakana.KatakanaSymbol
import com.mathsemilio.katakanalearner.others.katakanaSymbolsList
import com.mathsemilio.katakanalearner.game.model.ModelRequestEventListener
import kotlin.random.Random

class GameBackend : BaseObservable<GameBackend.Listener>(), ModelRequestEventListener {

    interface Listener {
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

    private lateinit var countDownTimer: CountDownTimer

    private val katakanaSymbolList = katakanaSymbolsList.toMutableList()

    private var difficultyCountDownTime = 0L
    private var currentCountDownTime = 0L
    private var score = 0
    private var progress = 0

    private var firstRomanizationGroupString = ""
    private var secondRomanizationGroupString = ""
    private var thirdRomanizationGroupString = ""
    private var fourthRomanizationGroupString = ""

    private fun startGame(difficultyValue: Int) {
        difficultyCountDownTime = getCountdownTimeBasedOnDifficultyValue(difficultyValue)

        katakanaSymbolList.shuffle()

        onGameScoreUpdated(score)
        onSymbolUpdated(katakanaSymbolList.first())
        generateRomanizationGroup()
        startTimer(difficultyCountDownTime)
    }

    private fun getCountdownTimeBasedOnDifficultyValue(difficultyValue: Int): Long {
        return when (difficultyValue) {
            GAME_DIFFICULTY_VALUE_BEGINNER -> COUNTDOWN_TIME_BEGINNER
            GAME_DIFFICULTY_VALUE_MEDIUM -> COUNTDOWN_TIME_MEDIUM
            GAME_DIFFICULTY_VALUE_HARD -> COUNTDOWN_TIME_HARD
            else -> throw IllegalArgumentException(ILLEGAL_GAME_DIFFICULTY_VALUE)
        }
    }

    private fun startTimer(difficultyCountdownTime: Long) {
        countDownTimer = object : CountDownTimer(difficultyCountdownTime, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                currentCountDownTime = (millisUntilFinished / 1000).also {
                    onCountDownTimeUpdated(it.toInt())
                }
            }

            override fun onFinish() {
                currentCountDownTime = 0L
                onGameTimeOver()
            }
        }
        countDownTimer.start()
    }

    private fun onPauseTimer() = countDownTimer.cancel()

    private fun resumeTimer() = startTimer(currentCountDownTime.times(1000L))

    private fun checkAnswer(selectedRomanization: String) {
        onPauseTimer()

        if (katakanaSymbolList.first().romanization == selectedRomanization) {
            onGameScoreUpdated(++score)
            onCorrectAnswer()
        } else
            onWrongAnswer()
    }

    private fun getNextSymbol() {
        onGameProgressUpdated(++progress)
        katakanaSymbolList.removeAt(0)
        onSymbolUpdated(katakanaSymbolList.first())

        if (katakanaSymbolList.size == 1) {
            generateRomanizationGroup()
            startTimer(difficultyCountDownTime)
            onGameFinished()
        } else {
            generateRomanizationGroup()
            startTimer(difficultyCountDownTime)
        }
    }

    private fun generateRomanizationGroup() {
        val romanizationList = arrayOf(
            "A", "I", "U", "E", "O", "KA", "KI", "KU", "KE", "KO", "SA", "SHI", "SU", "SE", "SO",
            "TA", "CHI", "TSU", "TE", "TO", "NA", "NI", "NU", "NE", "NO", "HA", "HI", "FU", "HE",
            "HO", "MA", "MI", "MU", "ME", "MO", "YA", "YU", "YO", "RA", "RI", "RU", "RE", "RO",
            "WA", "WO", "N"
        ).let { romanizationList ->
            romanizationList.shuffle()
            romanizationList.filterNot { it == katakanaSymbolList.first().romanization }
        }

        firstRomanizationGroupString = romanizationList.slice(0..11).random()
        secondRomanizationGroupString = romanizationList.slice(12..23).random()
        thirdRomanizationGroupString = romanizationList.slice(24..35).random()
        fourthRomanizationGroupString = romanizationList.slice(36..44).random()

        setCorrectRomanizationAnswer()

        onRomanizationGroupUpdated(
            listOf(
                firstRomanizationGroupString,
                secondRomanizationGroupString,
                thirdRomanizationGroupString,
                fourthRomanizationGroupString
            )
        )
    }

    private fun setCorrectRomanizationAnswer() {
        when (Random.nextInt(4)) {
            0 -> firstRomanizationGroupString = katakanaSymbolList.first().romanization
            1 -> secondRomanizationGroupString = katakanaSymbolList.first().romanization
            2 -> thirdRomanizationGroupString = katakanaSymbolList.first().romanization
            3 -> fourthRomanizationGroupString = katakanaSymbolList.first().romanization
        }
    }

    override fun onStartGameRequested(difficultyValue: Int) {
        startGame(difficultyValue)
    }

    override fun onCheckUserAnswerRequested(selectedRomanization: String) {
        checkAnswer(selectedRomanization)
    }

    override fun onGetNextSymbolRequested() {
        getNextSymbol()
    }

    override fun onPauseGameTimerRequested() {
        onPauseTimer()
    }

    override fun onResumeGameTimerRequested() {
        resumeTimer()
    }

    private fun onSymbolUpdated(symbol: KatakanaSymbol) {
        listeners.forEach { it.onSymbolUpdated(symbol) }
    }

    private fun onCountDownTimeUpdated(countDownTime: Int) {
        listeners.forEach { it.onGameCountdownTimeUpdated(countDownTime) }
    }

    private fun onCorrectAnswer() {
        listeners.forEach { it.onCorrectAnswer() }
    }

    private fun onWrongAnswer() {
        listeners.forEach { it.onWrongAnswer() }
    }

    private fun onGameTimeOver() {
        listeners.forEach { it.onGameTimeOver() }
    }

    private fun onGameFinished() {
        listeners.forEach { it.onGameFinished() }
    }

    private fun onGameScoreUpdated(score: Int) {
        listeners.forEach { it.onGameScoreUpdated(score) }
    }

    private fun onGameProgressUpdated(progress: Int) {
        listeners.forEach { it.onGameProgressUpdated(progress) }
    }

    private fun onRomanizationGroupUpdated(romanizationGroupList: List<String>) {
        listeners.forEach { it.onRomanizationGroupUpdated(romanizationGroupList) }
    }
}