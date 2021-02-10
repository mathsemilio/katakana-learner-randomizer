package com.mathsemilio.katakanalearner.ui.screens.game.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.mathsemilio.katakanalearner.data.preferences.repository.PreferencesRepository
import com.mathsemilio.katakanalearner.others.SoundEffectsModule
import com.mathsemilio.katakanalearner.ui.others.ScreensNavigator
import com.mathsemilio.katakanalearner.ui.screens.commom.BaseFragment
import com.mathsemilio.katakanalearner.ui.others.InterstitialAdHelper

class GameWelcomeScreen : BaseFragment(), GameWelcomeScreenView.Listener, InterstitialAdHelper.Listener {

    private lateinit var gameWelcomeScreenView: GameWelcomeScreenViewImpl

    private lateinit var preferencesRepository: PreferencesRepository
    private lateinit var soundEffectsModule: SoundEffectsModule
    private lateinit var screensNavigator: ScreensNavigator

    private lateinit var interstitialAdHelper: InterstitialAdHelper
    private lateinit var adRequest: AdRequest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gameWelcomeScreenView = compositionRoot.viewFactory.getGameWelcomeScreenView(container)
        return gameWelcomeScreenView.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()

        gameWelcomeScreenView.setupUI(preferencesRepository.gameDefaultOption)

        interstitialAdHelper.addListener(this)
    }

    private fun initialize() {
        preferencesRepository = compositionRoot.preferencesRepository

        soundEffectsModule = compositionRoot.soundEffectsModule

        screensNavigator = compositionRoot.screensNavigator

        adRequest = compositionRoot.adRequest

        interstitialAdHelper = compositionRoot.interstitialAdHelper
    }

    override fun onPlayClickSoundEffect() {
        soundEffectsModule.playClickSoundEffect()
    }

    override fun onSettingsIconClicked() {
        screensNavigator.navigateToSettingsScreen()
    }

    override fun onStartButtonClicked(difficultyValue: Int) {
        soundEffectsModule.playButtonClickSoundEffect()
        interstitialAdHelper.showInterstitialAd()
    }

    override fun onAdDismissed() {
        screensNavigator.navigateToMainScreen(gameWelcomeScreenView.getDifficultyValue())
    }

    override fun onAdFailedToLoad() {
        screensNavigator.navigateToMainScreen(gameWelcomeScreenView.getDifficultyValue())
    }

    override fun onStart() {
        gameWelcomeScreenView.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        gameWelcomeScreenView.removeListener(this)
        super.onStop()
    }

    override fun onDestroyView() {
        interstitialAdHelper.removeListener(this)
        super.onDestroyView()
    }
}