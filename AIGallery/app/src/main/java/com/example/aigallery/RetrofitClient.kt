package com.example.aigallery
// In a new file: SearchResult.kt
// In your file: RetrofitClient.kt

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Use the IP address you found
    private const val BASE_URL = "http://10.56.107.11:5000/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}