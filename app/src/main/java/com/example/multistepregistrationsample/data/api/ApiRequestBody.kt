package com.example.multistepregistrationsample.data.api

import androidx.room.Entity

/**
 * Defined a data class that will define structure of API request body
 */
data class ApiRequestBody(
    val farmManagerName: String,
    val farmManagerPhoneNumber: String,
    val phoneNumber: String,
    val village: String,
    val firstName: String,
    val idNumber: String,
    val lastName: String,
    val middleName: String
)
