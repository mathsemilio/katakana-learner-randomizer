package com.mathsemilio.katakanalearner.ui.others

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mathsemilio.katakanalearner.ui.screens.game.main.GameMainScreenViewImpl
import com.mathsemilio.katakanalearner.ui.screens.game.result.GameResultScreenViewImpl
import com.mathsemilio.katakanalearner.ui.screens.game.welcome.GameWelcomeScreenViewImpl

class ViewFactory(private val inflater: LayoutInflater) {

    fun getGameWelcomeScreenView(container: ViewGroup?) =
        GameWelcomeScreenViewImpl(inflater, container)

    fun getGameMainScreenView(container: ViewGroup?) =
        GameMainScreenViewImpl(inflater, container)

    fun getGameResultScreenView(container: ViewGroup?) =
        GameResultScreenViewImpl(inflater, container)
}