package com.mathsemilio.katakanalearner.others

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mathsemilio.katakanalearner.data.preferences.repository.PreferencesRepository

class TrainingNotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        TrainingNotificationHelper(applicationContext).notifyUser()
        PreferencesRepository(applicationContext).setTrainingNotificationSwitchState(false)
        return Result.success()
    }
}