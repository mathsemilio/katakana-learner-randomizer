package com.mathsemilio.katakanalearner.di

import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.mathsemilio.katakanalearner.data.preferences.repository.PreferencesRepository
import com.mathsemilio.katakanalearner.others.TrainingNotificationHelper
import com.mathsemilio.katakanalearner.others.soundeffects.SoundEffectsModule
import com.mathsemilio.katakanalearner.ui.others.*
import com.mathsemilio.katakanalearner.ui.screens.game.main.usecase.AlertUserUseCase
import com.mathsemilio.katakanalearner.ui.screens.game.main.viewmodel.GameMainScreenViewModel
import com.mathsemilio.katakanalearner.ui.screens.game.result.usecase.ShareGameScoreUseCase
import com.mathsemilio.katakanalearner.ui.usecase.ShowInterstitialAdUseCase

class ControllerCompositionRoot(
    private val compositionRoot: CompositionRoot,
    private val fragment: Fragment
) {
    private fun getFragmentContainerHelper(): FragmentContainerHelper {
        return fragment.requireActivity() as FragmentContainerHelper
    }

    fun getViewFactory(): ViewFactory {
        return compositionRoot.getViewFactory(LayoutInflater.from(fragment.requireContext()))
    }

    fun getPreferencesRepository(): PreferencesRepository {
        return compositionRoot.getPreferencesRepository(fragment.requireContext())
    }

    fun getSoundEffectsModule(volume: Float): SoundEffectsModule {
        return compositionRoot.getSoundEffectsModule(fragment.requireContext(), volume)
    }

    fun getScreensNavigator(): ScreensNavigator {
        return compositionRoot.getScreensNavigator(
            fragment.parentFragmentManager, getFragmentContainerHelper()
        )
    }

    fun getShowInterstitialAdUseCase(): ShowInterstitialAdUseCase {
        return compositionRoot.getShowInterstitialAdUseCase(
            fragment.requireActivity(),
            fragment.requireContext()
        )
    }

    fun getAdRequest(): AdRequest {
        return compositionRoot.getAdRequest()
    }

    fun getTrainingNotificationHelper(): TrainingNotificationHelper {
        return TrainingNotificationHelper(fragment.requireContext())
    }

    fun getToolbarVisibilityHelper(): ToolbarVisibilityHelper {
        return fragment.requireActivity() as ToolbarVisibilityHelper
    }

    fun getMessagesHelper(): MessagesHelper {
        return MessagesHelper(fragment.requireContext())
    }

    fun getDialogHelper(): DialogHelper {
        return compositionRoot.getDialogHelper(
            fragment.requireContext(),
            fragment.parentFragmentManager
        )
    }

    fun getAppThemeUtil(): AppThemeUtil {
        return compositionRoot.getAppThemeUtil(fragment.requireContext())
    }

    fun getGameMainScreenViewModel(): GameMainScreenViewModel {
        return GameMainScreenViewModel()
    }

    fun getBackPressedDispatcher(onBackPressed: () -> Unit) {
        fragment.requireActivity().onBackPressedDispatcher.addCallback(
            fragment.viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() = onBackPressed()
            }
        )
    }

    fun getAlertUserUseCase(): AlertUserUseCase {
        return AlertUserUseCase(fragment.requireContext(), fragment.parentFragmentManager)
    }

    fun getShareGameScoreUseCase(score: Int): ShareGameScoreUseCase {
        return ShareGameScoreUseCase(fragment.requireContext(), score)
    }
}