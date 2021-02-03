package com.mathsemilio.katakanalearner.ui.screens.game.main.usecase

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.mathsemilio.katakanalearner.commom.observable.BaseObservable
import com.mathsemilio.katakanalearner.ui.others.DialogHelper
import com.mathsemilio.katakanalearner.ui.screens.game.main.ScreenState

class AlertUserUseCase(context: Context, fragmentManager: FragmentManager) :
    BaseObservable<AlertUserUseCase.Listener>() {

    interface Listener {
        fun onPauseGameTimer()
        fun onScreenStateChanged(newScreenState: ScreenState)
        fun onPlayButtonClickSoundEffect()
        fun onPlaySuccessSoundEffect()
        fun onPlayErrorSoundEffect()
    }

    private val dialogHelper = DialogHelper(context, fragmentManager)

    fun alertUserOnExitGame(
        onDialogNegativeButtonClicked: () -> Unit,
        onDialogPositiveButtonClicked: () -> Unit
    ) {
        onPauseGameTimer()
        onPlayButtonClickSoundEffect()
        onChangeCurrentScreenState(ScreenState.DIALOG_BEING_SHOWN)
        dialogHelper.showExitGameDialog(
            { onDialogNegativeButtonClicked() },
            {
                onDialogPositiveButtonClicked()
                onChangeCurrentScreenState(ScreenState.TIMER_RUNNING)
            }
        )
    }

    fun alertUserOnGamePaused(onDialogPositiveButtonClicked: () -> Unit) {
        onPauseGameTimer()
        onPlayButtonClickSoundEffect()
        onChangeCurrentScreenState(ScreenState.DIALOG_BEING_SHOWN)
        dialogHelper.showGamePausedDialog {
            onDialogPositiveButtonClicked()
            onChangeCurrentScreenState(ScreenState.TIMER_RUNNING)
        }
    }

    fun alertUserOnCorrectAnswer(onDialogPositiveButtonClicked: () -> Unit) {
        onPlaySuccessSoundEffect()
        onChangeCurrentScreenState(ScreenState.DIALOG_BEING_SHOWN)
        dialogHelper.showCorrectAnswerDialog {
            onDialogPositiveButtonClicked()
            onChangeCurrentScreenState(ScreenState.TIMER_RUNNING)
        }
    }

    fun alertUserOnWrongAnswer(
        correctRomanization: String,
        onDialogPositiveButtonClicked: () -> Unit
    ) {
        onPlayErrorSoundEffect()
        onChangeCurrentScreenState(ScreenState.DIALOG_BEING_SHOWN)
        dialogHelper.showWrongAnswerDialog(correctRomanization) {
            onDialogPositiveButtonClicked()
            onChangeCurrentScreenState(ScreenState.TIMER_RUNNING)
        }
    }

    fun alertUserOnTimeOver(
        correctRomanization: String,
        onDialogPositiveButtonClicked: () -> Unit
    ) {
        onPlayErrorSoundEffect()
        onChangeCurrentScreenState(ScreenState.DIALOG_BEING_SHOWN)
        dialogHelper.showTimeOverDialog(correctRomanization) {
            onDialogPositiveButtonClicked()
            onChangeCurrentScreenState(ScreenState.TIMER_RUNNING)
        }
    }

    private fun onPauseGameTimer() {
        getListeners().forEach { it.onPauseGameTimer() }
    }

    private fun onChangeCurrentScreenState(newState: ScreenState) {
        getListeners().forEach { it.onScreenStateChanged(newState) }
    }

    private fun onPlayButtonClickSoundEffect() {
        getListeners().forEach { it.onPlayButtonClickSoundEffect() }
    }

    private fun onPlaySuccessSoundEffect() {
        getListeners().forEach { it.onPlaySuccessSoundEffect() }
    }

    private fun onPlayErrorSoundEffect() {
        getListeners().forEach { it.onPlayErrorSoundEffect() }
    }
}