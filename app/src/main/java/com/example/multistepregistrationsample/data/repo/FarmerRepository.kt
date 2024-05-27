package com.example.multistepregistrationsample.data.repo

import android.util.Log
import com.example.multistepregistrationsample.data.FarmerRegistrationData
import com.example.multistepregistrationsample.data.api.ApiRequestBody
import com.example.multistepregistrationsample.data.api.ApiService
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

    private suspend fun getAllFarmerRegistrations(): List<FarmerRegistrationData> {
        return farmerRegistrationDao.getAllFarmerRegistrations()
    }

    suspend fun saveFarmerRegOnline(apiRequestBody: ApiRequestBody): FarmerRegistrationAPIResponse {
        return apiService.saveFarmerRegData(apiRequestBody)
    }

    // sync data to online api
    suspend fun syncOfflineData() {
        val offlineData = farmerRegistrationDao.getAllFarmerRegistrations()
        for (data in offlineData) {
            try {
                // send offline data as requestBody to api
                /*
                val response = saveFarmerRegOnline(data)

                if (response.status == "success") {
                    Log.d("FarmerRepo successful ==0","${response.message}\n" +
                            "${response.data}")
                }

                 */
            } catch (e: Exception) {
                Log.d("FarmerRepo failed ==1","${e.message}")
            }
        }
    }
}