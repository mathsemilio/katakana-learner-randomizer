package com.mathsemilio.katakanalearner.commom.baseobservable

interface Observable<Listener> {
    fun addListener(listener: Listener)
    fun removeListener(listener: Listener)
}