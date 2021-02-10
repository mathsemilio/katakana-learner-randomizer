package com.mathsemilio.katakanalearner.ui.screens.game.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.android.gms.ads.AdRequest
import com.mathsemilio.katakanalearner.commom.ARG_DIFFICULTY_VALUE
import com.mathsemilio.katakanalearner.commom.NULL_DIFFICULTY_VALUE_EXCEPTION
import com.mathsemilio.katakanalearner.domain.entity.katakana.KatakanaSymbol
import com.mathsemilio.katakanalearner.domain.usecase.GetSymbolUseCase
import com.mathsemilio.katakanalearner.game.model.GameModel
import com.mathsemilio.katakanalearner.others.SoundEffectsModule
import com.mathsemilio.katakanalearner.ui.others.ScreensNavigator
import com.mathsemilio.katakanalearner.ui.screens.commom.BaseFragment
import com.mathsemilio.katakanalearner.ui.others.InterstitialAdHelper
import com.mathsemilio.katakanalearner.ui.screens.game.main.commom.AlertUserHelper
import com.mathsemilio.katakanalearner.ui.screens.game.main.commom.ScreenState

class GameMainScreen : BaseFragment(),
    GameMainScreenView.Listener,
    GameModel.Listener,
    AlertUserHelper.Listener,
    GetSymbolUseCase.Listener,
    InterstitialAdHelper.Listener {

    companion object {
        fun newInstance(difficultyValue: Int): GameMainScreen {
            val args = Bundle().apply { putInt(ARG_DIFFICULTY_VALUE, difficultyValue) }
            val gameMainScreenFragment = GameMainScreen()
            gameMainScreenFragment.arguments = args
            return gameMainScreenFragment
        }
    }

    private lateinit var gameMainScreenView: GameMainScreenViewImpl
    private lateinit var model: GameModel

    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var soundEffectsModule: SoundEffectsModule
    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var getSymbolUseCase: GetSymbolUseCase
    private lateinit var alertUserHelper: AlertUserHelper

    private lateinit var interstitialAdHelper: InterstitialAdHelper
    private lateinit var adRequest: AdRequest

    private var difficultyValue = 0
    private var currentScreenState = ScreenState.TIMER_RUNNING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        difficultyValue = getDifficultyValue()

        model = compositionRoot.gameMainScreenViewModel

        onBackPressedCallback = compositionRoot.getOnBackPressedCallback { onExitButtonClicked() }

        soundEffectsModule = compositionRoot.soundEffectsModule

        screensNavigator = compositionRoot.screensNavigator

        getSymbolUseCase = compositionRoot.getSymbolUseCase

        alertUserHelper = compositionRoot.alertUserHelper

        adRequest = compositionRoot.adRequest

        interstitialAdHelper = compositionRoot.interstitialAdHelper
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gameMainScreenView = compositionRoot.viewFactory.getGameMainScreenView(container)
        return gameMainScreenView.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameMainScreenView.setupUI(difficultyValue)

        model.addListener(this)
        model.startGame(difficultyValue)

        setupOnBackPressedDispatcher()
    }

    private fun getDifficultyValue(): Int {
        return arguments?.getInt(ARG_DIFFICULTY_VALUE) ?: throw RuntimeException(
            NULL_DIFFICULTY_VALUE_EXCEPTION
        )
    }

    private fun setupOnBackPressedDispatcher() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, onBackPressedCallback
        )
    }

    override fun onExitButtonClicked() {
        alertUserHelper.alertUserOnExitGame(
            { screensNavigator.navigateToWelcomeScreen() },
            { model.resumeGameTimer() })
    }

    override fun onPauseButtonClicked() {
        alertUserHelper.alertUserOnGamePaused { model.resumeGameTimer() }
    }

    override fun onCheckAnswerClicked(selectedRomanization: String) {
        model.checkUserAnswer(selectedRomanization)
    }

    override fun onGameScoreUpdated(newScore: Int) {
        gameMainScreenView.updateGameScoreTextView(newScore)
    }

    override fun onGameProgressUpdated(updatedProgress: Int) {
        gameMainScreenView.updateProgressBarGameProgressValue(updatedProgress)
    }

    override fun onGameCountDownTimeUpdated(updatedCountdownTime: Int) {
        gameMainScreenView.updateProgressBarGameTimerProgressValue(updatedCountdownTime)
    }

    override fun onRomanizationGroupUpdated(updatedRomanizationGroupList: List<String>) {
        gameMainScreenView.updateRomanizationOptionsGroup(updatedRomanizationGroupList)
    }

    override fun onCurrentKatakanaSymbolUpdated(newSymbol: KatakanaSymbol) {
        gameMainScreenView.updateCurrentKatakanaSymbol(newSymbol.symbol)
    }

    override fun onCorrectAnswer() {
        alertUserHelper.alertUserOnCorrectAnswer {
            getSymbolUseCase.getNextSymbol(model.gameFinished, model.currentGameScore)
            gameMainScreenView.clearRomanizationOptions()
        }
    }

    override fun onWrongAnswer() {
        alertUserHelper.alertUserOnWrongAnswer(model.currentKatakanaSymbol.romanization) {
            getSymbolUseCase.getNextSymbol(model.gameFinished, model.currentGameScore)
            gameMainScreenView.clearRomanizationOptions()
        }
    }

    override fun onGameTimeOver() {
        alertUserHelper.alertUserOnTimeOver(model.currentKatakanaSymbol.romanization) {
            getSymbolUseCase.getNextSymbol(model.gameFinished, model.currentGameScore)
            gameMainScreenView.clearRomanizationOptions()
        }
    }

    override fun onPauseGameTimer() {
        model.pauseGameTimer()
    }

    override fun onScreenStateChanged(newScreenState: ScreenState) {
        currentScreenState = newScreenState
    }

    override fun playClickSoundEffect() {
        soundEffectsModule.playClickSoundEffect()
    }

    override fun onPlayButtonClickSoundEffect() {
        soundEffectsModule.playButtonClickSoundEffect()
    }

    override fun onPlaySuccessSoundEffect() {
        soundEffectsModule.playSuccessSoundEffect()
    }

    override fun onPlayErrorSoundEffect() {
        soundEffectsModule.playErrorSoundEffect()
    }

    override fun onGetNextSymbol() {
        model.getNextSymbol()
    }

    override fun onShowInterstitialAd() {
        interstitialAdHelper.showInterstitialAd()
    }

    override fun onAdDismissed() {
        screensNavigator.navigateToResultScreen(difficultyValue, model.currentGameScore)
    }

    override fun onAdFailedToLoad() {
        screensNavigator.navigateToResultScreen(difficultyValue, model.currentGameScore)
    }

    override fun onPause() {
        model.pauseGameTimer()

        if (currentScreenState == ScreenState.TIMER_RUNNING)
            currentScreenState = ScreenState.TIMER_PAUSED

        super.onPause()
    }

    override fun onStop() {
        gameMainScreenView.removeListener(this)
        getSymbolUseCase.removeListener(this)
        alertUserHelper.removeListener(this)
        interstitialAdHelper.removeListener(this)
        super.onStop()
    }

    override fun onDestroyView() {
        model.onClearInstance()
        model.removeListener(this)
        super.onDestroyView()
    }

    override fun onStart() {
        gameMainScreenView.addListener(this)
        getSymbolUseCase.addListener(this)
        alertUserHelper.addListener(this)
        interstitialAdHelper.addListener(this)
        super.onStart()
    }

    override fun onResume() {
        if (currentScreenState == ScreenState.TIMER_PAUSED)
            model.resumeGameTimer()

        super.onResume()
    }
}