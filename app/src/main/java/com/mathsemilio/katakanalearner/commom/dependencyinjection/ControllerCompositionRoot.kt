package com.mathsemilio.katakanalearner.commom.dependencyinjection

import androidx.activity.OnBackPressedCallback
import com.mathsemilio.katakanalearner.game.model.GameModel
import com.mathsemilio.katakanalearner.others.notification.TrainingNotificationHelper
import com.mathsemilio.katakanalearner.ui.others.DialogHelper
import com.mathsemilio.katakanalearner.ui.others.MessagesHelper
import com.mathsemilio.katakanalearner.ui.others.ToolbarVisibilityHelper
import com.mathsemilio.katakanalearner.ui.others.InterstitialAdHelper
import com.mathsemilio.katakanalearner.domain.usecase.GetSymbolUseCase
import com.mathsemilio.katakanalearner.ui.screens.game.main.commom.AlertUserHelper
import com.mathsemilio.katakanalearner.ui.screens.game.result.commom.ShareGameScoreHelper

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    private val activity get() = activityCompositionRoot.getActivity()

    private val context get() = activityCompositionRoot.context

    private val fragmentManager get() = activityCompositionRoot.fragmentManager

    val adRequest get() = activityCompositionRoot.adRequest

    val alertUserHelper get() = AlertUserHelper(dialogHelper)

    val appThemeUtil get() = activityCompositionRoot.appThemeUtil

    val dialogHelper get() = DialogHelper(context, fragmentManager)

    val gameMainScreenViewModel get() = GameModel()

    val getSymbolUseCase get() = GetSymbolUseCase(preferencesRepository)

    val interstitialAdHelper get() = InterstitialAdHelper(activity, context, adRequest)

    val messagesHelper get() = MessagesHelper(context)

    val preferencesRepository get() = activityCompositionRoot.preferencesRepository

    val screensNavigator get() = activityCompositionRoot.screensNavigator

    val shareGameScoreHelper get() = ShareGameScoreHelper(context)

    val soundEffectsModule get() = activityCompositionRoot.soundEffectsModule

    val toolbarVisibilityHelper get() = activity as ToolbarVisibilityHelper

    val trainingNotificationHelper get() = TrainingNotificationHelper(context)

    val viewFactory get() = activityCompositionRoot.viewFactory

    fun getOnBackPressedCallback(onBackPressed: () -> Unit) =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = onBackPressed()
        }
}