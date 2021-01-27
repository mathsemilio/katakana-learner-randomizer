package com.mathsemilio.katakanalearner.logic.backend

import android.os.CountDownTimer
import com.mathsemilio.katakanalearner.commom.*
import com.mathsemilio.katakanalearner.domain.katakana.KatakanaSymbol
import com.mathsemilio.katakanalearner.others.katakanaSymbolsList
import kotlin.random.Random

class GameBackend : BaseObservable<BackendEventListener>(), ViewModelRequestEventListener {

    private lateinit var mCountDownTimer: CountDownTimer

    private val mKatakanaSymbolList = katakanaSymbolsList.toMutableList()

    private var mDifficultyCountDownTime = 0L
    private var mCurrentCountDownTime = 0L
    private var mScore = 0
    private var mProgress = 0

    private var mFirstRomanizationGroupString = ""
    private var mSecondRomanizationGroupString = ""
    private var mThirdRomanizationGroupString = ""
    private var mFourthRomanizationGroupString = ""

    private fun startGame(difficultyValue: Int) {
        mDifficultyCountDownTime = getCountdownTimeBasedOnDifficultyValue(difficultyValue)

        mKatakanaSymbolList.shuffle()

        gameScoreUpdated(mScore)
        symbolUpdated(mKatakanaSymbolList.first())
        generateRomanizationGroup()
        startTimer(mDifficultyCountDownTime)
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
        mCountDownTimer = object : CountDownTimer(difficultyCountdownTime, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                mCurrentCountDownTime = (millisUntilFinished / 1000).also {
                    countDownTimeUpdated(it.toInt())
                }
            }

            override fun onFinish() {
                mCurrentCountDownTime = 0L
                onGameTimeOver()
            }
        }
        mCountDownTimer.start()
    }

    private fun pauseTimer() = mCountDownTimer.cancel()

    private fun resumeTimer() = startTimer(mCurrentCountDownTime.times(1000L))

    private fun checkAnswer(selectedRomanization: String) {
        pauseTimer()

        if (mKatakanaSymbolList.first().romanization == selectedRomanization) {
            gameScoreUpdated(++mScore)
            correctAnswer()
        } else
            wrongAnswer()
    }

    private fun getNextSymbol() {
        gameProgressUpdated(++mProgress)
        mKatakanaSymbolList.removeAt(0)
        symbolUpdated(mKatakanaSymbolList.first())

        if (mKatakanaSymbolList.size == 1) {
            generateRomanizationGroup()
            startTimer(mDifficultyCountDownTime)
            gameFinished()
        } else {
            generateRomanizationGroup()
            startTimer(mDifficultyCountDownTime)
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
            romanizationList.filterNot { it == mKatakanaSymbolList.first().romanization }
        }

        mFirstRomanizationGroupString = romanizationList.slice(0..11).random()
        mSecondRomanizationGroupString = romanizationList.slice(12..23).random()
        mThirdRomanizationGroupString = romanizationList.slice(24..35).random()
        mFourthRomanizationGroupString = romanizationList.slice(36..44).random()

        setCorrectRomanizationAnswer()

        romanizationGroupUpdated(
            listOf(
                mFirstRomanizationGroupString,
                mSecondRomanizationGroupString,
                mThirdRomanizationGroupString,
                mFourthRomanizationGroupString
            )
        )
    }

    private fun setCorrectRomanizationAnswer() {
        when (Random.nextInt(4)) {
            0 -> mFirstRomanizationGroupString = mKatakanaSymbolList.first().romanization
            1 -> mSecondRomanizationGroupString = mKatakanaSymbolList.first().romanization
            2 -> mThirdRomanizationGroupString = mKatakanaSymbolList.first().romanization
            3 -> mFourthRomanizationGroupString = mKatakanaSymbolList.first().romanization
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
        pauseTimer()
    }

    override fun onResumeGameTimerRequested() {
        resumeTimer()
    }

    private fun symbolUpdated(symbol: KatakanaSymbol) {
        getListeners().forEach { it.onSymbolUpdated(symbol) }
    }

    private fun countDownTimeUpdated(countDownTime: Int) {
        getListeners().forEach { it.onGameCountdownTimeUpdated(countDownTime) }
    }

    private fun correctAnswer() {
        getListeners().forEach { it.onCorrectAnswer() }
    }

    private fun wrongAnswer() {
        getListeners().forEach { it.onWrongAnswer() }
    }

    private fun onGameTimeOver() {
        getListeners().forEach { it.onGameTimeOver() }
    }

    private fun gameFinished() {
        getListeners().forEach { it.onGameFinished() }
    }

    private fun gameScoreUpdated(score: Int) {
        getListeners().forEach { it.onGameScoreUpdated(score) }
    }

    private fun gameProgressUpdated(progress: Int) {
        getListeners().forEach { it.onGameProgressUpdated(progress) }
    }

    private fun romanizationGroupUpdated(romanizationGroupList: List<String>) {
        getListeners().forEach { it.onRomanizationGroupUpdated(romanizationGroupList) }
    }
}