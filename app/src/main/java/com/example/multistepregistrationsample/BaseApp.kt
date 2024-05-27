package com.example.multistepregistrationsample

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.multistepregistrationsample.data.workmanager.enqueueSyncWork
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApp : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()


    override fun onCreate() {
        super.onCreate()

        Log.e("APP LAUNCHES","WORK MANAGER INITIATED"+enqueueSyncWork(applicationContext))


    }
}