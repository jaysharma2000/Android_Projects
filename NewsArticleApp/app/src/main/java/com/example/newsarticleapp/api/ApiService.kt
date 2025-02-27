package com.example.newsarticleapp.api

import com.example.newsarticleapp.model.ArticleData
import com.example.newsarticleapp.model.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("everything")
    fun getArticles(@Query("q") nameOfSource: String, @Query("from") startDate:String,
                    @Query("sortBy") sortName:String, @Query("apiKey") apiKey:String ): Call<ArticleResponse>
}

