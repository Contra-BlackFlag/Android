package com.example.aigallery

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("path")
    val path: String,

    @SerializedName("score")
    val score: Double
)