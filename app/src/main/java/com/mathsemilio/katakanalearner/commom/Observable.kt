package com.mathsemilio.katakanalearner.commom

interface Observable<Listener> {
    fun registerListener(listener: Listener)
    fun removeListener(listener: Listener)
}