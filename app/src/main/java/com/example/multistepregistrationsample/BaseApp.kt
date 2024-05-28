package com.example.multistepregistrationsample

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
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
        //enqueueSyncWork(applicationContext)
        Log.e("APP LAUNCHES","==instance started successfully")
    }
}