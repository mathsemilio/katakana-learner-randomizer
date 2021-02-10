package com.mathsemilio.katakanalearner.ui.screens.game.main.commom

import com.mathsemilio.katakanalearner.commom.baseobservable.BaseObservable
import com.mathsemilio.katakanalearner.ui.others.DialogHelper

class AlertUserHelper(private val dialogHelper: DialogHelper) : BaseObservable<AlertUserHelper.Listener>() {

    interface Listener {
        fun onPauseGameTimer()
        fun onScreenStateChanged(newScreenState: ScreenState)
        fun onPlayButtonClickSoundEffect()
        fun onPlaySuccessSoundEffect()
        fun onPlayErrorSoundEffect()
    }

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
        listeners.forEach { it.onPauseGameTimer() }
    }

    private fun onChangeCurrentScreenState(newState: ScreenState) {
        listeners.forEach { it.onScreenStateChanged(newState) }
    }

    private fun onPlayButtonClickSoundEffect() {
        listeners.forEach { it.onPlayButtonClickSoundEffect() }
    }

    private fun onPlaySuccessSoundEffect() {
        listeners.forEach { it.onPlaySuccessSoundEffect() }
    }

    private fun onPlayErrorSoundEffect() {
        listeners.forEach { it.onPlayErrorSoundEffect() }
    }
}