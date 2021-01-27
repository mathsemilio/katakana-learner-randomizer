package com.mathsemilio.katakanalearner.ui.others

import android.content.Context
import android.widget.Toast
import com.mathsemilio.katakanalearner.R

class MessagesHelper(private val context: Context) {

    fun showTrainingReminderSetSuccessfullyMessage() {
        Toast.makeText(
            context,
            context.getString(R.string.preference_notification_set_toast_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun showTrainingReminderSetFailedMessage() {
        Toast.makeText(
            context,
            context.getString(R.string.preference_notification_time_in_future_toast_message),
            Toast.LENGTH_LONG
        ).show()
    }
}