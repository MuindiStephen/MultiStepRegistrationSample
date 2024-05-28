package com.example.multistepregistrationsample.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.multistepregistrationsample.data.ContactDetails
import com.example.multistepregistrationsample.data.FarmerDetails
import com.example.multistepregistrationsample.data.FarmerRegistrationData
import com.example.multistepregistrationsample.data.mappers.toApiRequestBody
import com.example.multistepregistrationsample.data.repo.FarmerRepository
import com.example.multistepregistrationsample.data.workmanager.enqueueSyncWork
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class FarmerRegistrationViewModel @Inject constructor(
    private val farmerRepository: FarmerRepository,
    @SuppressLint("StaticFieldLeak") @ApplicationContext private val context: Context
) : ViewModel() {

    private val _farmerRegistrationData = MutableLiveData<FarmerRegistrationData>()
    val farmerRegistrationData: LiveData<FarmerRegistrationData> get() = _farmerRegistrationData

    private val _registrationResult = MutableLiveData<Result<FarmerRegistrationData?>>()
    val registrationResult: LiveData<Result<FarmerRegistrationData?>> get() = _registrationResult

    fun updateFarmerDetails(details: FarmerDetails) {
       val currentData = _farmerRegistrationData.value ?: FarmerRegistrationData(
           farmerDetails = details, contactDetails = ContactDetails(
               "","","","",
           )
       )
        _farmerRegistrationData.value = currentData.copy(farmerDetails = details)
    }

    fun updateContactDetails(details: ContactDetails) {
        val currentData = _farmerRegistrationData.value ?: FarmerRegistrationData(
            farmerDetails = FarmerDetails("", "", "", ""),
            contactDetails = details
        )
        _farmerRegistrationData.value = currentData.copy(contactDetails = details)
    }

    // Similarly, add methods to update other parts of the data

    fun submitRegistration() {
        viewModelScope.launch {


                if (isOnline(context = context)) {
                    val attempts: Int =0

                    try {
                        val farmerData = _farmerRegistrationData.value!!
                        farmerRepository.saveFarmerRegistrationOffline(farmerData)
                        _registrationResult.value = Result.success(farmerData)
                        Log.e("VM","Farmer Reg: SUCCESS")
                        Log.e("VM", "DEVICE ONLINE")

                        // Also save farmer online - Api if connected to network
                        farmerRepository.saveFarmerRegOnline(toApiRequestBody(farmerData))

                        viewModelScope.launch {
                            delay(5000L)
                            enqueueSyncWork(context)
                        }


                    }catch (e: Exception) {

                        if (e is HttpException && e.code() == 429 && attempts <= 3) {
                            val retryAfter = e.response()?.headers()?.get("Retry-After")?.toLongOrNull() ?: "Default"
                            Log.i("VM", "Retrying after $retryAfter seconds...")
                            delay(3000L)
                            enqueueSyncWork(context)
                        } else {
                            Log.e("VM", "Online SAVE FAILED: ${e.message}")
                            _registrationResult.value = Result.failure(e)
                        }
                    }
                } else {
                    val farmerData = _farmerRegistrationData.value!!
                    farmerRepository.saveFarmerRegistrationOffline(farmerData)
                    _registrationResult.value = Result.success(farmerData)
                }
        }
    }

    @SuppressLint("MissingPermission")
    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

     fun syncOfflineData() {
        viewModelScope.launch {
            try {
               // farmerRepository.syncOfflineData()
            }catch (e: Exception) {
                Log.e("Viewmodel","failed sync")
            }
        }
    }
}
