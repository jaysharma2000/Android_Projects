package com.example.apiresponseapp.api

import retrofit2.http.GET
import com.example.apiresponseapp.model.Photos
import retrofit2.Call


interface ApiService {
    @GET("photos")
    fun getPhotos(): Call<List<Photos>>

}


