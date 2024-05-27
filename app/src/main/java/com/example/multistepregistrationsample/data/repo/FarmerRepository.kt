package com.example.multistepregistrationsample.data.repo

import android.util.Log
import com.example.multistepregistrationsample.data.FarmerRegistrationData
import com.example.multistepregistrationsample.data.api.ApiRequestBody
import com.example.multistepregistrationsample.data.api.ApiService
import com.example.multistepregistrationsample.data.mappers.toApiRequestBody
import com.example.multistepregistrationsample.data.responses.FarmerRegistrationAPIResponse
import com.example.multistepregistrationsample.data.room.AppDatabase
import com.example.multistepregistrationsample.data.room.FarmerRegistrationDao
import okhttp3.ResponseBody
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

    // Back-up or sync farmer data
    suspend fun saveFarmerRegOnline(apiRequestBody: ApiRequestBody): FarmerRegistrationAPIResponse {
        return apiService.saveFarmerRegData(apiRequestBody)
    }

    /**
    suspend fun syncOfflineData() {
        val offlineData = farmerRegistrationDao.getAllFarmerRegistrations()

        val req = offlineData.map { toApiRequestBody(it) }
        for (data in offlineData) {
            try {

              //  apiService.saveFarmerRegData(req)

                val respose = apiService.saveFarmerRegData(req)

                if (respose.status == "success") {
                    Log.d("FarmerRepo successful ==0","${respose.message}\n" +
                            "${respose.data}")
                } else {
                    Log.d("FarmerRepo successful ==1","failed to process request")
                }

            } catch (e: Exception) {
                Log.d("FarmerRepo failed ==1","${e.message}")
            }
        }
    }
    */
}