package com.example.multistepregistrationsample.data.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.multistepregistrationsample.data.mappers.toApiRequestBody
import com.example.multistepregistrationsample.data.repo.FarmerRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Work manager to schedule data syncing to API
 */
@HiltWorker
class SyncWorker @AssistedInject constructor(
     @Assisted context: Context,
     @Assisted workerParams: WorkerParameters,
     private val farmerRepository: FarmerRepository
) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result {

        return withContext(Dispatchers.IO) {
             try {

                val farmerDataList = farmerRepository.getAllFarmerRegistrations()
                if (farmerDataList.isNotEmpty()) {
                    val apiRequestBodies = farmerDataList.map { toApiRequestBody(it) }
                    farmerRepository.saveFarmerRegOnline(apiRequestBodies)
                }
                Result.success()
            } catch (e: Exception) {
                Result.retry()
            }
        }
    }


    @dagger.assisted.AssistedFactory
    interface Factory : ChildWorkerFactory {
        override fun create(appContext: Context, workerParameters: WorkerParameters): SyncWorker
    }
}


