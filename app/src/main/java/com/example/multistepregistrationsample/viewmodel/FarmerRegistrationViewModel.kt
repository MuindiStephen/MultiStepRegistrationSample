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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
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

                    try {
                        val farmerData = _farmerRegistrationData.value!!
                        farmerRepository.saveFarmerRegistrationOffline(farmerData)
                        _registrationResult.value = Result.success(farmerData)
                        Log.e("VM","Farmer Reg: SUCCESS")
                        Log.e("VM", "DEVICE ONLINE")

                      //  farmerRepository.saveFarmerRegOnline(listOf(toApiRequestBody(farmerData)))

//                        CoroutineScope(Dispatchers.IO).launch {
//                            delay(2000L)
//                            syncOfflineData()
//                        }

                        enqueueSyncWork(context)

                    }catch (e: Exception) {
                        Log.e("VM, FARMER REG","FAILED  ${e.message}")
                    }
                } else {
                    val farmerData = _farmerRegistrationData.value!!
                    farmerRepository.saveFarmerRegistrationOffline(farmerData)
                    _registrationResult.value = Result.success(farmerData)
                }
           /* if (isOnline(context)) {
                try {
                    farmerRepository.saveFarmerRegistrationOffline(farmerData)
                    _registrationResult.value = Result.success(null)
                   // val response = farmerRepository.registerMilkSupplierOnline(farmerData)
                  //  _registrationResult.value = Result.success(response)

                    syncOfflineData()
                } catch (e: Exception) {
                    _registrationResult.value = Result.failure(e)
                }


            } else {
                try {
                   farmerRepository.saveFarmerRegistrationOffline(farmerData)
                    _registrationResult.value = Result.success(null)
                } catch (e: Exception) {
                    _registrationResult.value = Result.failure(e)
                }
            }

            */
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
