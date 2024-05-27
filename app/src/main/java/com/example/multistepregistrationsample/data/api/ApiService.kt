package com.example.multistepregistrationsample.data.api

import com.example.multistepregistrationsample.data.FarmerRegistrationData
import com.example.multistepregistrationsample.data.responses.FarmerRegistrationAPIResponse
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * API interface to talk to server
 */
interface ApiService {

    @POST("create")
    suspend fun saveFarmerRegData(
        @Body apiRequestBody: ApiRequestBody
    ): FarmerRegistrationAPIResponse


    companion object {
        const val BASE_URL = "https://dummy.restapiexample.com/api/v1/"
    }
}