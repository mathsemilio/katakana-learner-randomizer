package com.mathsemilio.katakanalearner.commom.observable

abstract class BaseObservable<Listener> : Observable<Listener> {

    private val listeners = mutableSetOf<Listener>()

    override fun addListener(listener: Listener) { listeners.add(listener) }

    override fun removeListener(listener: Listener) { listeners.remove(listener) }

    protected fun getListeners() = listeners.toSet()
}