package com.example.mysmarthomecontrollerapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://6790fc47af8442fd737893e9.mockapi.io/smartHomeControllerApp/"

    val api: SmartHomeApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SmartHomeApi::class.java)
    }
}
