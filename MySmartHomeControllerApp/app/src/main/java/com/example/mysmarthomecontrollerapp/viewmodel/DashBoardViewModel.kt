package com.example.mysmarthomecontrollerapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysmarthomecontrollerapp.api.RetrofitClient
import kotlinx.coroutines.launch

class DashBoardViewModel : ViewModel() {

    private val smartLightStatusFromApi = MutableLiveData<String>("Unknown")
    val smartLightStatus: LiveData<String> get() = smartLightStatusFromApi

    private val thermostatStatusFromApi = MutableLiveData<String>("Unknown")
    val thermostatStatus: LiveData<String> get() = thermostatStatusFromApi

    private val fanSpeedStatusFromApi = MutableLiveData<String>("Unknown")
    val fanSpeedStatus: LiveData<String> get() = fanSpeedStatusFromApi

    fun fetchDevices() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getDevices()

                response.forEach { device ->
                    when (device.type) {
                        "light" -> smartLightStatusFromApi.postValue(device.status)
                        "thermostat" -> thermostatStatusFromApi.postValue(device.status)
                        "fan" -> fanSpeedStatusFromApi.postValue(device.status)
                    }
                }
            } catch (e: Exception) {
                Log.d(e.message, "Error fetching device status from api")
            }
        }
    }
}