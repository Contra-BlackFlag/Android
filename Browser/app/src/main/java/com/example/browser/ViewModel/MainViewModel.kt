package com.example.browser

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.browser.datastore.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val userPrefs : UserPreferences) : ViewModel() {
    private val _url = mutableStateOf("")
    val url = _url
    private val _currentUrl = mutableStateOf("")
    val currentUrl = _currentUrl

    private val _search = mutableIntStateOf(R.drawable.google)
    val search = _search
    private val _searchengine = mutableStateOf("google")
    val searchengine = _searchengine

    val textbox = mutableStateOf(false)

    fun urlrequest(url : String){
        _url.value = url
    }
    fun currentUrl(url : String){
        _currentUrl.value = url
    }

    private val _scroll = mutableStateOf(false)
    val scroll = _scroll

    fun setScroll(state : Boolean){
        _scroll.value = state
    }


    //DATASTORE

    val hapticsMode = userPrefs.hapticsFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    val searchEngineSelect = userPrefs.searchFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "google"
    )

    fun setHaptics(enabled: Boolean) {
        viewModelScope.launch {
            userPrefs.saveHaptics(enabled)
        }
    }

    fun setSearchEngine(search : String) {
        viewModelScope.launch {
            userPrefs.saveSearchEngine(search)
        }
    }
}