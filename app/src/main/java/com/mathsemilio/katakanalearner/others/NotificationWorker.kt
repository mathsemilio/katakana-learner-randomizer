package com.mathsemilio.katakanalearner.others

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mathsemilio.katakanalearner.commom.TrainingNotificationHelper
import com.mathsemilio.katakanalearner.data.preferences.repository.PreferencesRepository

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        TrainingNotificationHelper.notifyUser(applicationContext)
        PreferencesRepository(applicationContext).saveNotificationSwitchState(switchState = false)

        return Result.success()
    }
}