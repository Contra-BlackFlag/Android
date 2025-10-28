package com.example.browser

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _url = mutableStateOf("")
    val url = _url
    private val _currentUrl = mutableStateOf("")
    val currentUrl = _currentUrl

    fun urlrequest(url : String){
        _url.value = url
    }
    fun currentUrl(url : String){
        _currentUrl.value = url
    }
}