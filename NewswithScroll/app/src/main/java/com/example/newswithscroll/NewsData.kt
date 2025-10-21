package com.example.newswithscroll

import com.google.android.gms.auth.api.identity.AuthorizationClient
import retrofit2.http.Url

data class NewsData(
    val articles: List<Article>
)

data class Article(
    val urlToImage : String,
    val url: String,
    val author: String,
    // CRITICAL FIX: Changed 'Title' to 'title'
    val title: String,
    // CRITICAL FIX: Changed 'Content' to 'content'
    val content: String
)

data class NewsState(val loading : Boolean = true,
    val list : List<Article> = emptyList(),
    val error : String? = null)