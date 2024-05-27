package com.example.multistepregistrationsample.data

import androidx.room.Entity
import androidx.room.PrimaryKey
data class FarmerDetails(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val idNumber: String,
)

data class ContactDetails(
    val phoneNumber: String,
    val farmManagerName: String,
    val farmManagerPhoneNumber: String,
    val village: String
)

data class Questionnaire(
    val hasInsurance: String,
    val hasAnimalManagementTraining: String,
    val doesArtificialInsemination: String,
    val administersMinerals: String,
    val administersVaccines: String,
    val farmingPracticesLevel: String
)

data class AnimalsOwned(
    val animalBreed: String,
    val numberOfBulls: String,
    val numberOfCows: String
)

data class MainCrops(
    val cropType: String,
    val acresPlanted: String
)

data class FarmingDetails(
    val typeOfFarming: String,
    val waterSource: String,
    val averageWaterIntakePerCow: String,
    val totalDailyWaterIntake: String,
    val totalAverageMilkProductionDaily: String,
    val averageSupplyToGrader: String
)

data class SourceOfFeeds(
    val typeOfFeeding: String,
    val percentageConcentrates: String,
    val pasturePercentage: String,
    val totalMixedRationPerKg: String
)





@Entity(tableName = "farmer_registration")
data class FarmerRegistrationData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var farmerDetails: FarmerDetails,
    var contactDetails: ContactDetails
)

