package com.example.todo

data class LocationData(
    val latitude : Double,
    val longitude : Double
)
data class GeoCodingResponse(
    val result : List<GeoCodingResult>,
    val status : String
)
data class GeoCodingResult(
    val formatted_address: String
)
