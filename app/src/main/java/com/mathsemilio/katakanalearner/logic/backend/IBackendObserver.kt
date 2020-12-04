package com.mathsemilio.katakanalearner.logic.backend

import com.mathsemilio.katakanalearner.logic.event.BackendEvent

interface IBackendObserver {
    fun onBackendEvent(event: BackendEvent)
}