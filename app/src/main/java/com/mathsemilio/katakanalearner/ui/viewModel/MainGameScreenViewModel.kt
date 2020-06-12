package com.mathsemilio.katakanalearner.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mathsemilio.katakanalearner.data.katakanaLetters
import com.mathsemilio.katakanalearner.data.model.Katakana

private const val TAG_MAIN_GAME_SCREEN_VM = "MainGameScreenViewModel"

/**
 * ViewModel class that implements most of the game's logic
 */
class MainGameScreenViewModel : ViewModel() {

    //==========================================================================================
    // MutableLiveData variables for the UI elements
    //==========================================================================================
    private val _currentKatakanaLetterDrawableId = MutableLiveData<Int>()
    val currentKatakanaLetterDrawableId: LiveData<Int>
        get() = _currentKatakanaLetterDrawableId

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

    //==========================================================================================
    // MutableLiveData variables for game events
    //==========================================================================================
    private val _eventCorrectAnswer = MutableLiveData<Boolean>()
    val eventCorrectAnswer: LiveData<Boolean>
        get() = _eventCorrectAnswer

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    //==========================================================================================
    // Other variables
    //==========================================================================================
    val katakanaLettersList: MutableList<Katakana> = katakanaLetters.toMutableList()

    private var lastKatakanaLetterDrawableSymbolId: Int = 0
    private var lastKatakanaLetterLetterRomanization: String? = null

    //==========================================================================================
    // init block
    //==========================================================================================
    init {
        _gameScore.value = 0

        _eventGameFinished.value = false

        startGame()
    }

    //==========================================================================================
    // startGame function
    //==========================================================================================
    /**
     * Function that is responsible for key tasks necessary for starting the game.
     */
    private fun startGame() {
        Log.i(TAG_MAIN_GAME_SCREEN_VM, "startGame: Game Started")

        // Shuffling the katakanaLettersList list
        katakanaLettersList.shuffle()

        // Getting the first drawableSymbolId and romanization from the list
        _currentKatakanaLetterDrawableId.value = katakanaLettersList.first().drawableSymbolId
        _currentKatakanaLetterRomanization.value = katakanaLettersList.first().romanization.also {
            Log.d(TAG_MAIN_GAME_SCREEN_VM, "startGame: First letter: $it")
        }

        // Getting the last drawableSymbolId and romanization from the list
        lastKatakanaLetterDrawableSymbolId = katakanaLettersList.last().drawableSymbolId
        lastKatakanaLetterLetterRomanization = katakanaLettersList.last().romanization.also {
            Log.d(TAG_MAIN_GAME_SCREEN_VM, "startGame: Last letter: $it")
        }

        generateRadioButtonRomanization()
    }

    //==========================================================================================
    // checkUserInput function
    //==========================================================================================
    /**
     * Function responsible for checking the user's input (answer).
     *
     * @param selectedRomanization - String of the current checked radio button
     */
    fun checkUserInput(selectedRomanization: String) {
        /*
        Checking if the current romanization equals the selected romanization, if it is, the
        answer is correct and the game score is updated, else it's incorrect
        */
        if (_currentKatakanaLetterRomanization.value == selectedRomanization) {
            Log.d(TAG_MAIN_GAME_SCREEN_VM, "checkUserInput: Answer correct")
            _eventCorrectAnswer.value = true

            updateGameScore()
        } else {
            Log.d(TAG_MAIN_GAME_SCREEN_VM, "checkUserInput: Incorrect answer")
            _eventCorrectAnswer.value = false
        }
    }

    //==========================================================================================
    // getNextLetter function
    //==========================================================================================
    /**
     * Function responsible for removing the current letter, and getting the next one from the
     * list.
     */
    fun getNextLetter() {
        // Removing the first element (Katakana letter) from the list
        katakanaLettersList.removeAt(0)

        // Getting the first letter from the katakanaLettersList list
        _currentKatakanaLetterDrawableId.value = katakanaLettersList.first().drawableSymbolId
        _currentKatakanaLetterRomanization.value = katakanaLettersList.first().romanization.also {
            Log.d(TAG_MAIN_GAME_SCREEN_VM, "getNextLetter: Next letter: $it")
        }

        generateRadioButtonRomanization()
    }

    //==========================================================================================
    // getLastLetter function
    //==========================================================================================
    /**
     * Function responsible for getting the last letter from the list and setting its contents
     * to the UI. It also checks the user input and finishes the game.
     */
    fun getLastLetter(selectedRomanization: String) {
        Log.d(TAG_MAIN_GAME_SCREEN_VM, "getLastLetter: Setting last letter")
        /*
         Setting the value of the current katakana letter as the value of the last letter
         from the list
        */
        _currentKatakanaLetterDrawableId.value = lastKatakanaLetterDrawableSymbolId
        _currentKatakanaLetterRomanization.value = lastKatakanaLetterLetterRomanization

        if (_currentKatakanaLetterRomanization.value == selectedRomanization) {
            Log.d(TAG_MAIN_GAME_SCREEN_VM, "getLastLetter: Correct answer, game finished")
            _eventCorrectAnswer.value = true

            updateGameScore()

            // Setting the value of _eventGameFinished as TRUE to finish the game
            _eventGameFinished.value = true
        } else {
            Log.d(TAG_MAIN_GAME_SCREEN_VM, "getLastLetter: Incorrect answer, game finished")
            _eventCorrectAnswer.value = false

            // Setting the value of _eventGameFinished as TRUE to finish the game
            _eventGameFinished.value = true
        }
    }

    //==========================================================================================
    // updateGameScore function
    //==========================================================================================
    /**
     * Function that increments the game score by 1
     */
    private fun updateGameScore() {
        Log.d(TAG_MAIN_GAME_SCREEN_VM, "updateGameScore: Incrementing game score")
        _gameScore.value = (_gameScore.value)?.inc().also {
            Log.d(TAG_MAIN_GAME_SCREEN_VM, "updateGameScore: New value: $it")
        }
    }

    //==========================================================================================
    // generateRadioButtonRomanizations function
    //==========================================================================================
    /**
     * Function that generates random romanizations for the radio buttons. It also selects which
     * button will receive the current letter romanization (the correct answer).
     */
    private fun generateRadioButtonRomanization() {
        // List containing romanizations to be used as distractions
        val katakanaRomanizationList = listOf(
            "A", "I", "U", "E", "O", "KA", "KI", "KU", "KE", "KO", "SA", "SHI", "SU", "SE", "SO",
            "TA", "CHI", "TSU", "TSE", "TO", "NA", "NI", "NU", "NE", "NO", "HA", "HI", "FU", "HE",
            "HO", "MA", "MI", "MU", "ME", "MO", "YA", "YU", "YO", "RA", "RI", "RU", "RE", "RO",
            "WA", "WI", "WE", "WO", "N"
        )

        /*
         List that takes the katakanaRomanizationList, applies a filter (to remove the
         romanization that matches the current one), and shuffles it.
        */
        val filteredList =
            katakanaRomanizationList.filterNot { it == _currentKatakanaLetterRomanization.value }
                .shuffled()

        // Getting a random romanization for each radio button from the filteredList
        _radioButton1Romanization.value = filteredList.slice(0..13).random().also {
            Log.d(
                TAG_MAIN_GAME_SCREEN_VM,
                "generateRadioButtonRomanization: Random romanization for Radio Button 1: $it"
            )
        }

        _radioButton2Romanization.value = filteredList.slice(14..27).random().also {
            Log.d(
                TAG_MAIN_GAME_SCREEN_VM,
                "generateRadioButtonRomanization: Random romanization for Radio Button 2: $it"
            )
        }

        _radioButton3Romanization.value = filteredList.slice(28..42).random().also {
            Log.d(
                TAG_MAIN_GAME_SCREEN_VM,
                "generateRadioButtonRomanization: Random romanization for Radio Button 3: $it"
            )
        }

        _radioButton4Romanization.value = filteredList.slice(43..46).random().also {
            Log.d(
                TAG_MAIN_GAME_SCREEN_VM,
                "generateRadioButtonRomanization: Random romanization for Radio Button 4: $it"
            )
        }

        /*
        Generating a random number between 0 and 4, and based on that number, a radio button
        will be selected to contain the current romanization for the letter on the screen.
        */
        when ((0 until 4).random()) {
            0 -> _radioButton1Romanization.value = _currentKatakanaLetterRomanization.value.also {
                Log.d(
                    TAG_MAIN_GAME_SCREEN_VM,
                    "generateRadioButtonRomanization: Radio Button 1 selected to contain the " +
                            "correct answer"
                )
            }
            1 -> _radioButton2Romanization.value = _currentKatakanaLetterRomanization.value.also {
                Log.d(
                    TAG_MAIN_GAME_SCREEN_VM,
                    "generateRadioButtonRomanization: Radio Button 2 selected to contain the " +
                            "correct answer"
                )
            }
            2 -> _radioButton3Romanization.value = _currentKatakanaLetterRomanization.value.also {
                Log.d(
                    TAG_MAIN_GAME_SCREEN_VM,
                    "generateRadioButtonRomanization: Radio Button 3 selected to contain the " +
                            "correct answer"
                )
            }
            3 -> _radioButton4Romanization.value = _currentKatakanaLetterRomanization.value.also {
                Log.d(
                    TAG_MAIN_GAME_SCREEN_VM,
                    "generateRadioButtonRomanization: Radio Button 4 selected to contain the " +
                            "correct answer"
                )
            }
        }
    }
}