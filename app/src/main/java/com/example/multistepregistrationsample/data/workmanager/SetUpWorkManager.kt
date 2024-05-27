package com.example.multistepregistrationsample.data.workmanager

// WorkManagerSetup.kt or within a method in ViewModel/Repository
import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

fun enqueueSyncWork(context: Context) {
    val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "SyncWork",
        ExistingWorkPolicy.KEEP,
        syncRequest
    )
}

