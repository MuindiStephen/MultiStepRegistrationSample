package com.example.multistepregistrationsample.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.multistepregistrationsample.data.FarmerRegistrationData

@Dao
interface FarmerRegistrationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFarmerRegistration(farmerRegistrationData: FarmerRegistrationData)

    @Query("SELECT * FROM farmer_registration WHERE id = :id")
    suspend fun getFarmerRegistrationById(id: Long): FarmerRegistrationData?

    @Query("SELECT * FROM farmer_registration")
    suspend fun getAllFarmerRegistrations(): List<FarmerRegistrationData>
}
