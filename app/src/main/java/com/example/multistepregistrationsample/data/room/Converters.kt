package com.example.multistepregistrationsample.data.room

import androidx.room.TypeConverter
import com.example.multistepregistrationsample.data.ContactDetails
import com.example.multistepregistrationsample.data.FarmerDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromFarmerDetails(value: FarmerDetails): String {
        val gson = Gson()
        val type = object : TypeToken<FarmerDetails>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toFarmerDetails(value: String): FarmerDetails {
        val gson = Gson()
        val type = object : TypeToken<FarmerDetails>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromContactDetails(value: ContactDetails): String {
        val gson = Gson()
        val type = object : TypeToken<ContactDetails>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toContactDetails(value: String): ContactDetails {
        val gson = Gson()
        val type = object : TypeToken<ContactDetails>() {}.type
        return gson.fromJson(value,type)
    }
}
