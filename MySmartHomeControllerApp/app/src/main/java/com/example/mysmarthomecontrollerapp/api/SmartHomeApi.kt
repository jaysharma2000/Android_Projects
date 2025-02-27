package com.example.mysmarthomecontrollerapp.api

import com.example.mysmarthomecontrollerapp.model.Device
import retrofit2.http.GET

interface SmartHomeApi {
    @GET("devices")
    suspend fun getDevices(): List<Device>

}