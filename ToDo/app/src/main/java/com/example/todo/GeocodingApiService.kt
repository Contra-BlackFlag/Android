package com.example.todo

import android.R
import com.google.android.gms.common.api.internal.ApiKey
import com.google.android.gms.maps.model.LatLng
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    @GET("maps/api/geocode/json")
    suspend fun getAddressfromCoordinate(
            @Query("latlng") latlng: String,
            @Query("key") apiKey: String
    ) : GeoCodingResponse
}