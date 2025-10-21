package com.example.newswithscroll.Viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswithscroll.Api.recipientService
import com.example.newswithscroll.NewsState
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _NewsDataState = mutableStateOf(NewsState())
    val NewsDataState : State<NewsState> = _NewsDataState

    init {
        fetchNewsData()
    }

    private fun fetchNewsData(){
        viewModelScope.launch {
            try {
                val response = recipientService.getAllNews("palghar")
                if (response.isSuccessful) {
                    val data = response.body()
                    _NewsDataState.value = _NewsDataState.value.copy(
                        loading = false,
                        error = null,
                        // Pass the whole list of articles (or emptyList() if data is null)
                        list = data?.articles ?: emptyList()
                    )

                } else {
                    Log.e("NewsError", "Failed: ${response.code()}")
                }
            } catch (e: Exception) {
               _NewsDataState.value = _NewsDataState.value.copy(
                   loading = false,
                   error = "${e.message}"
               )
            }
        }
    }




}