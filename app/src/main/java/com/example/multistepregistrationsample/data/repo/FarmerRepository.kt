package com.example.multistepregistrationsample.data.repo

import android.util.Log
import androidx.work.ListenableWorker
import com.example.multistepregistrationsample.data.FarmerRegistrationData
import com.example.multistepregistrationsample.data.api.ApiRequestBody
import com.example.multistepregistrationsample.data.api.ApiService
import com.example.multistepregistrationsample.data.mappers.toApiRequestBody
import com.example.multistepregistrationsample.data.responses.FarmerRegistrationAPIResponse
import com.example.multistepregistrationsample.data.room.FarmerRegistrationDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FarmerRepository @Inject constructor(
    private val farmerRegistrationDao: FarmerRegistrationDao,
    private val apiService: ApiService
) {

    // save to offline room data storage
    suspend fun saveFarmerRegistrationOffline(farmerRegistrationData: FarmerRegistrationData) {
        farmerRegistrationDao.insertFarmerRegistration(farmerRegistrationData)
    }

    suspend fun getAllFarmerRegistrations(): List<FarmerRegistrationData> {
        return farmerRegistrationDao.getAllFarmerRegistrations()
    }

    // save farmer to api when online
    suspend fun saveFarmerRegOnline(apiRequestBody: ApiRequestBody): FarmerRegistrationAPIResponse {

        val response = apiService.saveFarmerRegData(apiRequestBody)
        if (response.status == "success")
        {
            Log.e("FarmerRepository","Successful")
        } else {
            Log.e("FarmerRepository","Failed")
        }
        return apiService.saveFarmerRegData(apiRequestBody)
    }

    // back-up or sync farmer offline data [local] to api when online
    suspend fun syncData(apiRequestBody: ApiRequestBody): FarmerRegistrationAPIResponse {
        return apiService.syncDataBatch(apiRequestBody)
    }



    /*
    suspend fun syncOfflineData() {
        delay(5000L)
        CoroutineScope(Dispatchers.IO).launch {
            val farmerDataList = farmerRegistrationDao.getAllFarmerRegistrations()

            farmerDataList.forEach { farmerRegistrationData ->
                val request = toApiRequestBody(farmerRegistrationData)
                val response = syncData(request)
                if (response.status=="success") {
                    ListenableWorker.Result.success()
                } else {
                    ListenableWorker.Result.Retry.failure()
                }
            }
        }
    }

     */

}