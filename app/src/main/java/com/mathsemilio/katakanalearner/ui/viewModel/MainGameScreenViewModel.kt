package com.mathsemilio.katakanalearner.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mathsemilio.katakanalearner.data.katakanaLetters
import com.mathsemilio.katakanalearner.data.model.Katakana

class MainGameScreenViewModel : ViewModel() {

    private val _katakanaLetterDrawableId = MutableLiveData<Int>()
    val katakanaLetterDrawableId: LiveData<Int>
        get() = _katakanaLetterDrawableId

    private val _currentKatakanaLetterRomanization = MutableLiveData<String>()
    val currentKatakanaLetterRomanization: LiveData<String>
        get() = _currentKatakanaLetterRomanization

    private val _radioButton1Romanization = MutableLiveData<String>()
    val radioButton1Romanization: LiveData<String>
        get() = _radioButton1Romanization

    private val _radioButton2Romanization = MutableLiveData<String>()
    val radioButton2Romanization: LiveData<String>
        get() = _radioButton2Romanization

    private val _radioButton3Romanization = MutableLiveData<String>()
    val radioButton3Romanization: LiveData<String>
        get() = _radioButton3Romanization

    private val _radioButton4Romanization = MutableLiveData<String>()
    val radioButton4Romanization: LiveData<String>
        get() = _radioButton4Romanization

    private val _gameScore = MutableLiveData<Short>()
    val gameScore: LiveData<Short>
        get() = _gameScore

    private val _eventCorrectAnswer = MutableLiveData<Boolean>()
    val eventCorrectAnswer: LiveData<Boolean>
        get() = _eventCorrectAnswer

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    val katakanaLettersList: MutableList<Katakana> = katakanaLetters.toMutableList()

    private var lastKatakanaLetterDrawableSymbolId: Int = 0
    private var lastKatakanaLetterLetterRomanization: String? = null

    init {
        _gameScore.value = 0

        _eventGameFinished.value = false

        startGame()
    }

    private fun startGame() {
        katakanaLettersList.shuffle()

        _katakanaLetterDrawableId.value = katakanaLettersList.first().drawableSymbolId
        _currentKatakanaLetterRomanization.value = katakanaLettersList.first().romanization

        lastKatakanaLetterDrawableSymbolId = katakanaLettersList.last().drawableSymbolId
        lastKatakanaLetterLetterRomanization = katakanaLettersList.last().romanization
    }

    fun checkUserInput(selectedRomanization: String) {
        if (_currentKatakanaLetterRomanization.value == selectedRomanization) {
            _eventCorrectAnswer.value = true

            updateGameScore()
        } else {
            _eventCorrectAnswer.value = false
        }
    }

    fun getNextLetter() {
        katakanaLettersList.removeAt(0)

        _katakanaLetterDrawableId.value = katakanaLettersList.first().drawableSymbolId
        _currentKatakanaLetterRomanization.value = katakanaLettersList.first().romanization

        generateRadioButtonRomanizations()
    }

    fun getLastLetter(selectedRomanization: String) {
        _katakanaLetterDrawableId.value = lastKatakanaLetterDrawableSymbolId
        _currentKatakanaLetterRomanization.value = lastKatakanaLetterLetterRomanization

        if (_currentKatakanaLetterRomanization.value == selectedRomanization) {
            _eventCorrectAnswer.value = true

            updateGameScore()

            _eventGameFinished.value = true
        } else {
            _eventCorrectAnswer.value = false

            _eventGameFinished.value = true
        }
    }

    private fun updateGameScore() {
        _gameScore.value = (_gameScore.value)?.inc()
    }

    private fun generateRadioButtonRomanizations() {
        val katakanaRomanizationList: List<String> = listOf(
            "A", "I", "U", "E", "O", "KA", "KI", "KU", "KE", "KO", "SA", "SHI", "SU", "SE", "SO",
            "TA", "CHI", "TSU", "TE", "TO", "NA", "NI", "NU", "NE", "NO", "HA", "HI", "FU", "HE",
            "HO", "MA", "MI", "MU", "ME", "MO", "YA", "YU", "YO", "RA", "RI", "RU", "RE", "RO",
            "WA", "WI", "WE", "WO", "N"
        )

        val filteredKatakanaRomanizationList =
            katakanaRomanizationList.filterNot {
                it == _currentKatakanaLetterRomanization.value
            }.shuffled()

        val radioButton1RomanizationIndex = generateRandomNumber(null)
        val radioButton2RomanizationIndex = generateRandomNumber(radioButton1RomanizationIndex)
        val radioButton3RomanizationIndex = generateRandomNumber(radioButton2RomanizationIndex)
        val radioButton4RomanizationIndex = generateRandomNumber(radioButton3RomanizationIndex)

        _radioButton1Romanization.value =
            filteredKatakanaRomanizationList[radioButton1RomanizationIndex]

        _radioButton2Romanization.value =
            filteredKatakanaRomanizationList[radioButton2RomanizationIndex]

        _radioButton3Romanization.value =
            filteredKatakanaRomanizationList[radioButton3RomanizationIndex]

        _radioButton4Romanization.value =
            filteredKatakanaRomanizationList[radioButton4RomanizationIndex]

        when ((0 until 4).random()) {
            0 -> _radioButton1Romanization.value = _currentKatakanaLetterRomanization.value
            1 -> _radioButton2Romanization.value = _currentKatakanaLetterRomanization.value
            2 -> _radioButton3Romanization.value = _currentKatakanaLetterRomanization.value
            3 -> _radioButton4Romanization.value = _currentKatakanaLetterRomanization.value
        }
    }

    private fun generateRandomNumber(previousNumber: Int?): Int {
        return (0 until 47).filterNot {
            it == previousNumber
        }.random()
    }
}