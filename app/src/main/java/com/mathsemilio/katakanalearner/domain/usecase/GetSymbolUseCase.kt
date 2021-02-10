package com.mathsemilio.katakanalearner.domain.usecase

import com.mathsemilio.katakanalearner.commom.PERFECT_SCORE
import com.mathsemilio.katakanalearner.commom.baseobservable.BaseObservable
import com.mathsemilio.katakanalearner.data.preferences.repository.PreferencesRepository

class GetSymbolUseCase(private val preferencesRepository: PreferencesRepository) :
    BaseObservable<GetSymbolUseCase.Listener>() {

    interface Listener {
        fun onGetNextSymbol()
        fun onShowInterstitialAd()
    }

    fun getNextSymbol(gameIsFinished: Boolean, score: Int) {
        if (gameIsFinished) {
            if (score == PERFECT_SCORE)
                preferencesRepository.incrementPerfectScoresValue()

            onShowInterstitialAd()
        } else {
            onGetNextSymbol()
        }
    }

    private fun onGetNextSymbol() {
        listeners.forEach { it.onGetNextSymbol() }
    }

    private fun onShowInterstitialAd() {
        listeners.forEach { it.onShowInterstitialAd() }
    }
}