package com.mathsemilio.katakanalearner.commom.observable

interface Observable<Listener> {
    fun addListener(listener: Listener)
    fun removeListener(listener: Listener)
}