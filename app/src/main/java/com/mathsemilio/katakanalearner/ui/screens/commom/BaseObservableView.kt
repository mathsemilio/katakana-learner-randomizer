package com.mathsemilio.katakanalearner.ui.screens.commom

import android.content.Context
import android.view.View
import com.mathsemilio.katakanalearner.commom.BaseObservable

abstract class BaseObservableView<Listener> : BaseObservable<Listener>(), IView {

    private lateinit var mRootView: View

    override fun getRootView() = mRootView

    protected fun setRootView(rootView: View) {
        mRootView = rootView
    }

    protected fun <T : View> findViewById(id: Int): T = getRootView().findViewById(id)

    protected fun getContext(): Context = getRootView().context
}