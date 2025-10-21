package com.example.newswithscroll.Api

import androidx.room.Query
import androidx.room.RawQuery
import com.example.newswithscroll.NewsData
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET


private val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
    .addConverterFactory(GsonConverterFactory.create()).build()

val recipientService = retrofit.create(ApiService::class.java)
val api = "5b84f0df8ba1489cb6b2e89935df7479"
interface ApiService {

    @GET("everything")
    suspend fun getAllNews(
        @retrofit2.http.Query("q") query: String,
        @retrofit2.http.Query("apikey") apikey : String = api
        ) : retrofit2.Response<NewsData>
}