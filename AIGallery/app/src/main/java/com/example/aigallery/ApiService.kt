// In ApiService.kt
package com.example.aigallery

import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun searchImages(@Query("query") searchText: String): List<SearchResult>

    // --- NEW: Endpoint for uploading an image ---
    @Multipart
    @POST("add_image")
    suspend fun addImage(@Part image: MultipartBody.Part): GenericResponse
}

// We also need a simple data class for the success/error response from the /add_image endpoint
data class GenericResponse(val success: Boolean, val message: String)