package com.mathsemilio.katakanalearner.logic.backend

import com.mathsemilio.katakanalearner.logic.event.BackendEvent

interface IObserverContract {
    fun registerObserver(IBackendObserver: IBackendObserver)
    fun removeObserver(IBackendObserver: IBackendObserver)
    fun notifyObserver(event: BackendEvent)
}