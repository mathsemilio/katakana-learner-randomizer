package com.mathsemilio.katakanalearner.ui.screens.commom

import android.content.Context
import android.view.View
import com.mathsemilio.katakanalearner.commom.observable.BaseObservable

abstract class BaseObservableView<Listener> : BaseObservable<Listener>(), IView {

    private lateinit var rootView: View

    override fun getRootView() = rootView

    protected fun setRootView(view: View) { rootView = view }

    protected fun <T : View> findViewById(id: Int): T = getRootView().findViewById(id)

    protected fun getContext(): Context = getRootView().context
}