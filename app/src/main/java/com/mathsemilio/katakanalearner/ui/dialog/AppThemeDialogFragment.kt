package com.mathsemilio.katakanalearner.ui.dialog

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.commom.*
import com.mathsemilio.katakanalearner.ui.commom.util.AppThemeUtil

class AppThemeDialogFragment : DialogFragment() {

    private lateinit var appThemeUtil: AppThemeUtil

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        appThemeUtil = AppThemeUtil(requireContext())

        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it).apply {
                setTitle(getString(R.string.app_theme_dialog_title))
                setSingleChoiceItems(getThemeArray(), getDefaultOption())
                { _, which ->
                    when (which) {
                        0 -> appThemeUtil.setLightAppTheme()
                        1 -> appThemeUtil.setDarkAppTheme()
                        2 -> appThemeUtil.setFollowSystemAppTheme()
                    }
                }
                setNegativeButton(getString(R.string.alert_dialog_cancel_button_text))
                { _, _ -> dialog?.cancel() }
            }
            builder.create()
        } ?: throw IllegalStateException(NULL_ACTIVITY_EXCEPTION)
    }

    private fun getThemeArray(): Int {
        return when (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            true -> R.array.app_theme_array_sdk_version_below_q
            else -> R.array.app_theme_array
        }
    }

    private fun getDefaultOption(): Int {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            when (appThemeUtil.getAppThemeValue()) {
                APP_THEME_LIGHT_THEME -> 0
                APP_THEME_DARK_THEME -> 1
                else -> throw IllegalArgumentException(INVALID_APP_THEME_VALUE_EXCEPTION)
            }
        } else {
            when (appThemeUtil.getAppThemeValue()) {
                APP_THEME_LIGHT_THEME -> 0
                APP_THEME_DARK_THEME -> 1
                APP_THEME_FOLLOW_SYSTEM -> 2
                else -> throw IllegalArgumentException(INVALID_APP_THEME_VALUE_EXCEPTION)
            }
        }
    }
}