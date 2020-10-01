package com.mathsemilio.katakanalearner.others

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesPerfectScores(context: Context) {

    companion object {
        const val SHARED_PREF_PERFECT_SCORES = "sharedPreferencesPerfectScores"
        const val PERFECT_SCORES = "perfectScores"
    }

    private val sharedPreferencesPerfectScores: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_PERFECT_SCORES, 0)

    private val editor: SharedPreferences.Editor = sharedPreferencesPerfectScores.edit()

    fun incrementPerfectScore() {
        editor.apply {
            putInt(
                PERFECT_SCORES,
                sharedPreferencesPerfectScores.getInt(PERFECT_SCORES, 0).inc()
            )
            apply()
        }
    }

    fun retrievePerfectScore(): Int {
        return sharedPreferencesPerfectScores.getInt(PERFECT_SCORES, 0)
    }

    fun clearPerfectScores() {
        editor.apply {
            clear()
            apply()
        }
    }
}