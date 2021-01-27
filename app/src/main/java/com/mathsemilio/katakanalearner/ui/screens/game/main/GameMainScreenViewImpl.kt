package com.mathsemilio.katakanalearner.ui.screens.game.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.commom.*
import com.mathsemilio.katakanalearner.ui.screens.commom.BaseObservableView

class GameMainScreenViewImpl(inflater: LayoutInflater, container: ViewGroup?) :
    BaseObservableView<GameMainScreenView.Listener>(), GameMainScreenView {

    private lateinit var mTextViewGameDifficulty: TextView
    private lateinit var mTextViewGameScore: TextView
    private lateinit var mProgressBarGameTimer: ProgressBar
    private lateinit var mTextViewCurrentKatakanaSymbol: TextView
    private lateinit var mChipGroupRomanizationOptions: ChipGroup
    private lateinit var mChipRomanizationOption1: Chip
    private lateinit var mChipRomanizationOption2: Chip
    private lateinit var mChipRomanizationOption3: Chip
    private lateinit var mChipRomanizationOption4: Chip
    private lateinit var mButtonExitGame: FloatingActionButton
    private lateinit var mButtonPauseGame: FloatingActionButton
    private lateinit var mButtonCheckAnswer: FloatingActionButton
    private lateinit var mProgressBarGameProgress: ProgressBar

    private var mSelectedRomanization = ""

    init {
        setRootView(inflater.inflate(R.layout.game_main_screen, container, false))
        initializeViews()
    }

    override fun onControllerViewCreated(difficultyValue: Int) {
        setGameDifficultyTextBasedOnDifficultyValue(difficultyValue)
        setupGameTimerProgressBarMax(difficultyValue)
        attachRomanizationOptionsChipGroupOnCheckChangedListener()
        attachClickListeners()
    }

    override fun setGameDifficultyTextBasedOnDifficultyValue(difficultyValue: Int) {
        mTextViewGameDifficulty.text = when (difficultyValue) {
            GAME_DIFFICULTY_VALUE_BEGINNER -> getContext().getString(R.string.game_difficulty_beginner)
            GAME_DIFFICULTY_VALUE_MEDIUM -> getContext().getString(R.string.game_difficulty_medium)
            GAME_DIFFICULTY_VALUE_HARD -> getContext().getString(R.string.game_difficulty_hard)
            else -> throw IllegalArgumentException(ILLEGAL_GAME_DIFFICULTY_VALUE)
        }
    }

    override fun updateGameScoreTextView(newScore: Int) {
        mTextViewGameScore.text = getContext().getString(R.string.game_score, newScore)
    }

    override fun updateCurrentKatakanaSymbol(newSymbol: String) {
        mTextViewCurrentKatakanaSymbol.text = newSymbol
    }

    override fun updateProgressBarGameTimerProgressValue(updatedCountdownTime: Int) {
        mProgressBarGameTimer.progress = updatedCountdownTime
    }

    override fun updateRomanizationOptionsGroup(updatedRomanizationList: List<String>) {
        mChipRomanizationOption1.text = updatedRomanizationList[0]
        mChipRomanizationOption2.text = updatedRomanizationList[1]
        mChipRomanizationOption3.text = updatedRomanizationList[2]
        mChipRomanizationOption4.text = updatedRomanizationList[3]
    }

    override fun updateProgressBarGameProgressValue(updatedProgress: Int) {
        mProgressBarGameProgress.progress = updatedProgress
    }

    override fun clearRomanizationOptions() {
        mChipGroupRomanizationOptions.clearCheck()
    }

    private fun initializeViews() {
        mTextViewGameDifficulty = findViewById(R.id.text_body_game_difficulty)
        mTextViewGameScore = findViewById(R.id.text_body_game_score)
        mProgressBarGameTimer = findViewById(R.id.game_timer_progress_bar)
        mTextViewCurrentKatakanaSymbol = findViewById(R.id.text_headline_current_katakana_letter)
        mChipGroupRomanizationOptions = findViewById(R.id.chip_group_romanization_options)
        mChipRomanizationOption1 = findViewById(R.id.chip_button_option_1)
        mChipRomanizationOption2 = findViewById(R.id.chip_button_option_2)
        mChipRomanizationOption3 = findViewById(R.id.chip_button_option_3)
        mChipRomanizationOption4 = findViewById(R.id.chip_button_option_4)
        mButtonExitGame = findViewById(R.id.fab_exit)
        mButtonPauseGame = findViewById(R.id.fab_pause)
        mButtonCheckAnswer = findViewById(R.id.fab_check_answer)
        mProgressBarGameProgress = findViewById(R.id.progress_bar_game_progress)
    }

    private fun setupGameTimerProgressBarMax(difficultyValue: Int) {
        mProgressBarGameTimer.max = when (difficultyValue) {
            GAME_DIFFICULTY_VALUE_BEGINNER -> PROGRESS_BAR_MAX_VALUE_BEGINNER
            GAME_DIFFICULTY_VALUE_MEDIUM -> PROGRESS_BAR_MAX_VALUE_MEDIUM
            GAME_DIFFICULTY_VALUE_HARD -> PROGRESS_BAR_MAX_VALUE_HARD
            else -> throw IllegalArgumentException(ILLEGAL_GAME_DIFFICULTY_VALUE)
        }
    }

    private fun attachRomanizationOptionsChipGroupOnCheckChangedListener() {
        mChipGroupRomanizationOptions.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == -1)
                mButtonCheckAnswer.isEnabled = false
            else {
                playClickSoundEffect()
                mButtonCheckAnswer.isEnabled = true
                val checkedChip = group.findViewById<Chip>(checkedId)
                mSelectedRomanization = checkedChip.text.toString()
            }
        }
    }

    private fun attachClickListeners() {
        mButtonExitGame.setOnClickListener { exitGameButtonClicked() }
        mButtonPauseGame.setOnClickListener { pauseGameButtonClicked() }
        mButtonCheckAnswer.setOnClickListener { checkAnswerButtonClicked(mSelectedRomanization) }
    }

    private fun playClickSoundEffect() {
        getListeners().forEach { it.playClickSoundEffect() }
    }

    private fun exitGameButtonClicked() {
        getListeners().forEach { it.onExitButtonClicked() }
    }

    private fun pauseGameButtonClicked() {
        getListeners().forEach { it.onPauseButtonClicked() }
    }

    private fun checkAnswerButtonClicked(selectedRomanization: String) {
        getListeners().forEach { it.onCheckAnswerClicked(selectedRomanization) }
    }
}