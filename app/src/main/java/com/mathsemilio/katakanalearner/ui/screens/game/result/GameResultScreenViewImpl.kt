package com.mathsemilio.katakanalearner.ui.screens.game.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.commom.*
import com.mathsemilio.katakanalearner.ui.screens.commom.BaseObservableView

class GameResultScreenViewImpl(inflater: LayoutInflater, container: ViewGroup?) :
    BaseObservableView<GameResultScreenView.Listener>(), GameResultScreenView {

    private lateinit var textViewYouGotSymbolsCorrectly: TextView
    private lateinit var textViewGameDifficulty: TextView
    private lateinit var textViewGamePerfectScores: TextView
    private lateinit var buttonHome: FloatingActionButton
    private lateinit var buttonPlayAgain: FloatingActionButton
    private lateinit var buttonShareScore: FloatingActionButton
    private lateinit var adViewGameResultScreen: AdView

    private var gameDifficultyValue = 0

    init {
        setRootView(inflater.inflate(R.layout.game_result_screen, container, false))
        initializeViews()
        attachClickListeners()
    }

    override fun setupUI(difficultyValue: Int, score: Int, perfectScores: Int) {
        gameDifficultyValue = difficultyValue

        if (score == 0) buttonShareScore.visibility = View.GONE

        setupYouGotSymbolsCorrectlyTextView(score)
        setupGameDifficultyTextView()
        setupPerfectScoresNumberTextView(perfectScores)
    }

    override fun loadBannerAd(adRequest: AdRequest) {
        adViewGameResultScreen.loadAd(adRequest)
    }

    private fun initializeViews() {
        textViewYouGotSymbolsCorrectly = findViewById(R.id.text_headline_you_got_correctly)
        textViewGameDifficulty = findViewById(R.id.text_headline_game_difficulty_score_screen)
        textViewGamePerfectScores = findViewById(R.id.text_headline_perfect_scores_number_score_screen)
        buttonHome = findViewById(R.id.fab_home)
        buttonPlayAgain = findViewById(R.id.fab_play_again)
        buttonShareScore = findViewById(R.id.fab_share)
        adViewGameResultScreen = findViewById(R.id.adview_game_result_screen)
    }

    private fun setupYouGotSymbolsCorrectlyTextView(score: Int) {
        textViewYouGotSymbolsCorrectly.text = when (score) {
            1 -> getContext().getString(R.string.you_got_one_symbol_correctly, score)
            PERFECT_SCORE -> getContext().getString(R.string.you_got_all_symbols_correctly)
            else -> getContext().getString(R.string.you_got_symbol_correctly, score)
        }
    }

    private fun setupGameDifficultyTextView() {
        textViewGameDifficulty.text = when (gameDifficultyValue) {
            GAME_DIFFICULTY_VALUE_BEGINNER -> getContext().getString(R.string.game_difficulty_beginner)
            GAME_DIFFICULTY_VALUE_MEDIUM -> getContext().getString(R.string.game_difficulty_medium)
            GAME_DIFFICULTY_VALUE_HARD -> getContext().getString(R.string.game_difficulty_hard)
            else -> throw IllegalArgumentException(ILLEGAL_GAME_DIFFICULTY_VALUE)
        }
    }

    private fun setupPerfectScoresNumberTextView(perfectScores: Int) {
        textViewGamePerfectScores.text = perfectScores.toString()
    }

    private fun attachClickListeners() {
        buttonHome.setOnClickListener { onHomeButtonClicked() }
        buttonPlayAgain.setOnClickListener { onPlayAgainButtonClicked(gameDifficultyValue) }
        buttonShareScore.setOnClickListener { onShareScoreButtonClicked() }
    }

    private fun onHomeButtonClicked() {
        listeners.forEach { it.onHomeButtonClicked() }
    }

    private fun onPlayAgainButtonClicked(difficultyValue: Int) {
        listeners.forEach { it.onPlayAgainClicked(difficultyValue) }
    }

    private fun onShareScoreButtonClicked() {
        listeners.forEach { it.onShareScoreButtonClicked() }
    }
}