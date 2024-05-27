package com.example.multistepregistrationsample.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.multistepregistrationsample.data.repo.FarmerRepository

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//@Module
//@InstallIn(SingletonComponent::class)
//object  WorkerModule {
//
//    @Singleton
//    @Provides
//    fun provideSyncWorkerFactory(
//        appContext: Context, workerParameters: WorkerParameters, farmerRepository: FarmerRepository
//    ): SyncWorker {
//        return SyncWorker(appContext, workerParameters, farmerRepository)
//    }
//}



