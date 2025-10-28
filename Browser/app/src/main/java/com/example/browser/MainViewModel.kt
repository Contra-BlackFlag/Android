package com.example.browser

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _url = mutableStateOf("")
    val url = _url
    private val _currentUrl = mutableStateOf("")
    val currentUrl = _currentUrl

    private val _search = mutableIntStateOf(R.drawable.google)
    val search = _search
    private val _searchengine = mutableStateOf("google")
    val searchengine = _searchengine

    fun urlrequest(url : String){
        _url.value = url
    }
    fun currentUrl(url : String){
        _currentUrl.value = url
    }
}