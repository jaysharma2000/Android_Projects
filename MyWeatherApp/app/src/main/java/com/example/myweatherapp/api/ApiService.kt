package com.example.myweatherapp.api

import com.example.myweatherapp.model.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("weather")
    fun getWeather(@Query("q") city:String, @Query("appid") apiKey:String): Call<WeatherData>
}

