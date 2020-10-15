package com.mathsemilio.katakanalearner.ui.viewModel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mathsemilio.katakanalearner.data.katakanaSymbolsList
import com.mathsemilio.katakanalearner.data.model.KatakanaSymbol
import com.mathsemilio.katakanalearner.others.GAME_DIFFICULTY_VALUE_BEGINNER
import com.mathsemilio.katakanalearner.others.GAME_DIFFICULTY_VALUE_MEDIUM
import kotlin.random.Random

/**
 * ViewModel class that implements most of the games and UI logic.
 */
class MainGameViewModel(gameDifficultyValue: Int) : ViewModel() {

    companion object {
        const val ONE_SECOND = 1000L
        const val COUNTDOWN_TIME_BEGINNER = 15000L
        const val COUNTDOWN_TIME_MEDIUM = 10000L
        const val COUNTDOWN_TIME_HARD = 5000L

        const val PROGRESS_BAR_MAX_BEGINNER = 14
        const val PROGRESS_BAR_MAX_MEDIUM = 9
        const val PROGRESS_BAR_MAX_HARD = 4
    }

    //==========================================================================================
    // MutableLiveData variables for the UI elements
    //==========================================================================================
    private val _currentKatakanaSymbolString = MutableLiveData<String>()
    val currentKatakanaLetterString: LiveData<String>
        get() = _currentKatakanaSymbolString

    private val _currentKatakanaSymbolRomanization = MutableLiveData<String>()
    val currentKatakanaLetterRomanization: LiveData<String>
        get() = _currentKatakanaSymbolRomanization

    private val _chip1StringRomanization = MutableLiveData<String>()
    val chip1StringRomanization: LiveData<String>
        get() = _chip1StringRomanization

    private val _chip2StringRomanization = MutableLiveData<String>()
    val chip2StringRomanization: LiveData<String>
        get() = _chip2StringRomanization

    private val _chip3StringRomanization = MutableLiveData<String>()
    val chip3StringRomanization: LiveData<String>
        get() = _chip3StringRomanization

    private val _chip4StringRomanization = MutableLiveData<String>()
    val chip4StringRomanization: LiveData<String>
        get() = _chip4StringRomanization

    private val _gameProgress = MutableLiveData<Int>()
    val gameProgress: LiveData<Int>
        get() = _gameProgress

    private val _gameScore = MutableLiveData<Int>()
    val gameScore: LiveData<Int>
        get() = _gameScore

    private val _currentGameTime = MutableLiveData<Long>()
    val currentGameTime: LiveData<Long>
        get() = _currentGameTime

    val currentGameTimeInt = Transformations.map(currentGameTime) { it.toInt() }

    //==========================================================================================
    // MutableLiveData variables for game events
    //==========================================================================================
    private val _eventCorrectAnswer = MutableLiveData<Boolean>()
    val eventCorrectAnswer: LiveData<Boolean>
        get() = _eventCorrectAnswer

    private val _eventTimeOver = MutableLiveData<Boolean>()
    val eventTimeOver: LiveData<Boolean>
        get() = _eventTimeOver

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    //==========================================================================================
    // Other variables
    //==========================================================================================
    val katakanaSymbolsMutableList: MutableList<KatakanaSymbol> =
        katakanaSymbolsList.toMutableList()

    private var lastKatakanaSymbolString = ""
    private var lastKatakanaSymbolRomanization = ""

    lateinit var countDownTimer: CountDownTimer
    private var difficultyCountDownTime: Long
    var gameTimerProgressBarValue: Int

    init {
        _gameProgress.value = 0

        _gameScore.value = 0

        _eventGameFinished.value = false

        difficultyCountDownTime = when (gameDifficultyValue) {
            GAME_DIFFICULTY_VALUE_BEGINNER -> COUNTDOWN_TIME_BEGINNER
            GAME_DIFFICULTY_VALUE_MEDIUM -> COUNTDOWN_TIME_MEDIUM
            else -> COUNTDOWN_TIME_HARD
        }

        gameTimerProgressBarValue = when (gameDifficultyValue) {
            GAME_DIFFICULTY_VALUE_BEGINNER -> PROGRESS_BAR_MAX_BEGINNER
            GAME_DIFFICULTY_VALUE_MEDIUM -> PROGRESS_BAR_MAX_MEDIUM
            else -> PROGRESS_BAR_MAX_HARD
        }

        startGame()
    }

    private fun startGame() {
        katakanaSymbolsMutableList.shuffle()

        _currentKatakanaSymbolString.value = katakanaSymbolsMutableList.first().symbol
        _currentKatakanaSymbolRomanization.value = katakanaSymbolsMutableList.first().romanization

        lastKatakanaSymbolString = katakanaSymbolsMutableList.last().symbol
        lastKatakanaSymbolRomanization = katakanaSymbolsMutableList.last().romanization

        generateChipGroupRomanization()

        startGameTimer(difficultyCountDownTime)
    }

    fun startGameTimer(countDownTime: Long) {
        countDownTimer = object : CountDownTimer(countDownTime, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentGameTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                cancel()
                _eventTimeOver.value = true
            }
        }
        countDownTimer.start()
    }

    fun checkUserInput(selectedRomanization: String) {
        if (_currentKatakanaSymbolRomanization.value == selectedRomanization) {
            _eventCorrectAnswer.value = true

            incrementGameScore()
        } else {
            _eventCorrectAnswer.value = false
        }
    }

    fun getNextLetter() {
        katakanaSymbolsMutableList.removeAt(0)

        _currentKatakanaSymbolString.value = katakanaSymbolsMutableList.first().symbol
        _currentKatakanaSymbolRomanization.value = katakanaSymbolsMutableList.first().romanization

        generateChipGroupRomanization()

        incrementGameProgress()

        startGameTimer(difficultyCountDownTime)
    }

    fun getLastLetter(selectedRomanization: String) {
        _currentKatakanaSymbolString.value = lastKatakanaSymbolString
        _currentKatakanaSymbolRomanization.value = lastKatakanaSymbolRomanization

        if (_currentKatakanaSymbolRomanization.value == selectedRomanization) {
            _eventCorrectAnswer.value = true

            incrementGameScore()

            incrementGameProgress()

            _eventGameFinished.value = true
        } else {
            incrementGameProgress()

            _eventCorrectAnswer.value = false

            _eventGameFinished.value = true
        }
    }

    private fun incrementGameScore() {
        _gameScore.value = (_gameScore.value)?.inc()
    }

    private fun incrementGameProgress() {
        _gameProgress.value = (_gameProgress.value)?.inc()
    }

    private fun generateChipGroupRomanization() {
        val katakanaRomanizationList = listOf(
            "A", "I", "U", "E", "O", "KA", "KI", "KU", "KE", "KO", "SA", "SHI", "SU", "SE", "SO",
            "TA", "CHI", "TSU", "TE", "TO", "NA", "NI", "NU", "NE", "NO", "HA", "HI", "FU", "HE",
            "HO", "MA", "MI", "MU", "ME", "MO", "YA", "YU", "YO", "RA", "RI", "RU", "RE", "RO",
            "WA", "WI", "WE", "WO", "N"
        )

        val filteredList =
            katakanaRomanizationList.filterNot { it == _currentKatakanaSymbolRomanization.value }
                .shuffled()

        _chip1StringRomanization.value = filteredList.slice(0..13).random()

        _chip2StringRomanization.value = filteredList.slice(14..27).random()

        _chip3StringRomanization.value = filteredList.slice(28..42).random()

        _chip4StringRomanization.value = filteredList.slice(43..46).random()

        when (Random.nextInt(4)) {
            0 -> _chip1StringRomanization.value = _currentKatakanaSymbolRomanization.value
            1 -> _chip2StringRomanization.value = _currentKatakanaSymbolRomanization.value
            2 -> _chip3StringRomanization.value = _currentKatakanaSymbolRomanization.value
            3 -> _chip4StringRomanization.value = _currentKatakanaSymbolRomanization.value
        }
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer.cancel()
    }
}