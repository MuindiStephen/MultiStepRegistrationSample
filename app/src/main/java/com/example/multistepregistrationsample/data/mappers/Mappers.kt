package com.example.multistepregistrationsample.data.mappers

import com.example.multistepregistrationsample.data.FarmerRegistrationData
import com.example.multistepregistrationsample.data.api.ApiRequestBody

/*
 Map FarmerRegistrationData to ApiRequest that will act as Request Body to API
 */
fun FarmerRegistrationData.toApiRequestBody(farmerRegistrationData: FarmerRegistrationData): ApiRequestBody {
    return ApiRequestBody(
        farmManagerName = farmerRegistrationData.contactDetails.farmManagerName,
        farmManagerPhoneNumber = farmerRegistrationData.contactDetails.farmManagerPhoneNumber,
        phoneNumber = farmerRegistrationData.contactDetails.phoneNumber,
        village = farmerRegistrationData.contactDetails.village,
        firstName = farmerRegistrationData.farmerDetails.firstName,
        idNumber = farmerRegistrationData.farmerDetails.idNumber,
        lastName = farmerRegistrationData.farmerDetails.lastName,
        middleName = farmerRegistrationData.farmerDetails.middleName
    )
}