package com.mathsemilio.katakanalearner.logic.event

sealed class GameEvent {
    object AnswerIsCorrect : GameEvent()
    object AnswerIsWrong : GameEvent()
    object TimeIsOver : GameEvent()
    object Paused : GameEvent()
    object Exit : GameEvent()
}