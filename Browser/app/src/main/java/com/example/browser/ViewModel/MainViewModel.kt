package com.example.browser

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.browser.Database.UserHistoryRepository
import com.example.browser.Database.userHistory
import com.example.browser.Database.userHistoryDB
import com.example.browser.datastore.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    context: Context,
    private val userPrefs: UserPreferences
) : ViewModel() {

    // Create the singleton DB instance & repository
    private val historyDao = userHistoryDB.getDatabase(context).userHistoryDao()
    private val repository = UserHistoryRepository(historyDao)

    val allHistory = repository.getAllHistory

    // Insert, update, delete
    fun insertHistory(title: String, url: String) {
        viewModelScope.launch {
            repository.insertHistory(userHistory(0, title, url))
        }
    }

    fun updateHistory(history: userHistory) {
        viewModelScope.launch {
            repository.updateHistory(history)
        }
    }

    fun deleteHistory(history: userHistory) {
        viewModelScope.launch {
            repository.deleteHistory(history)
        }
    }

    // Other existing variables
    private val _url = mutableStateOf("")
    val url = _url

    private val _currentUrl = mutableStateOf("")
    val currentUrl = _currentUrl

    private val _search = mutableIntStateOf(R.drawable.google)
    val search = _search

    private val _searchengine = mutableStateOf("google")
    val searchengine = _searchengine

    val textbox = mutableStateOf(false)

    fun urlrequest(url: String) {
        _url.value = url
    }

    fun currentUrl(url: String) {
        _currentUrl.value = url
    }

    private val _scroll = mutableStateOf(false)
    val scroll = _scroll

    fun setScroll(state: Boolean) {
        _scroll.value = state
    }

    // ---------------------- DATASTORE SECTION ----------------------

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

    fun setSearchEngine(search: String) {
        viewModelScope.launch {
            userPrefs.saveSearchEngine(search)
        }
    }

    val blurSlider = userPrefs.blurFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0.3F
    )

    fun setBlur(blur: Float) {
        viewModelScope.launch {
            userPrefs.saveBlur(blur)
        }
    }
}
