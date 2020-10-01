package com.mathsemilio.katakanalearner.others

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesSwitchState(context: Context) {

    companion object {
        const val SHARED_PREF_SWITCH_STATE = "sharedPreferencesSwitchState"
        const val SWITCH_STATE = "switchState"
    }

    private val sharedPreferencesPerfectScores: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_SWITCH_STATE, 0)

    private val editor: SharedPreferences.Editor = sharedPreferencesPerfectScores.edit()

    fun saveSwitchState(state: Boolean) {
        editor.apply {
            putBoolean(SWITCH_STATE, state)
            apply()
        }
    }

    fun retrieveSwitchState(): Boolean {
        return sharedPreferencesPerfectScores.getBoolean(SWITCH_STATE, false)
    }
}