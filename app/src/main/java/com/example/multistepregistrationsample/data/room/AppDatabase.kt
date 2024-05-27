package com.example.multistepregistrationsample.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.multistepregistrationsample.data.FarmerRegistrationData

@Database(entities = [FarmerRegistrationData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun farmerRegistrationDao(): FarmerRegistrationDao
}
