package com.mathsemilio.katakanalearner.ui.dialogFragment

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.others.APP_THEME_DARK_MODE
import com.mathsemilio.katakanalearner.others.APP_THEME_FOLLOW_SYSTEM
import com.mathsemilio.katakanalearner.others.APP_THEME_LIGHT_THEME
import com.mathsemilio.katakanalearner.others.SharedPreferencesAppTheme

/**
 * DialogFragment class for the changing the application theme within the Settings fragment.
 */
class AppThemeDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it).apply {
                setTitle(getString(R.string.app_theme_dialog_title))
                setSingleChoiceItems(
                    getThemeArray(), getDefaultOption(it)
                ) { _, which ->
                    when (which) {
                        0 -> {
                            AppCompatDelegate
                                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                            SharedPreferencesAppTheme(requireContext()).saveThemeValue(
                                APP_THEME_LIGHT_THEME
                            )
                        }
                        1 -> {
                            AppCompatDelegate
                                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                            SharedPreferencesAppTheme(requireContext()).saveThemeValue(
                                APP_THEME_DARK_MODE
                            )
                        }
                        2 -> {
                            AppCompatDelegate
                                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

                            SharedPreferencesAppTheme(requireContext()).saveThemeValue(
                                APP_THEME_FOLLOW_SYSTEM
                            )
                        }
                    }
                }
                setNegativeButton(getString(R.string.app_theme_dialog_negative_button_text)) { _, _ -> dialog?.cancel() }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun getDefaultOption(context: Context): Int {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            when (SharedPreferencesAppTheme(context).retrieveThemeValue()) {
                APP_THEME_LIGHT_THEME -> 0
                else -> 1
            }
        } else {
            when (SharedPreferencesAppTheme(context).retrieveThemeValue()) {
                APP_THEME_LIGHT_THEME -> 0
                APP_THEME_DARK_MODE -> 1
                else -> 2
            }
        }
    }

    private fun getThemeArray(): Int {
        return when (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            true -> R.array.app_theme_array_sdk_version_below_q
            else -> R.array.app_theme_array
        }
    }
}