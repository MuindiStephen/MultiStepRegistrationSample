package com.example.multistepregistrationsample.data.workmanager

// WorkManagerSetup.kt or within a method in ViewModel/Repository
import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun enqueueSyncWork(context: Context) {
    val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(15,TimeUnit.MINUTES)
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "SyncWork",
        ExistingPeriodicWorkPolicy.KEEP,
        syncRequest
    )
}

