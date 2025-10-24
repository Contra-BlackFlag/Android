package com.example.newsproject.ViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.newsproject.Api.recipientService
import com.example.newsproject.Data.NewsData
import com.example.newsproject.Data.NewsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    private val _NewsDataState = mutableStateOf(NewsState())
    val NewsDataState  = _NewsDataState

    private val _NewsDataStateCricket = mutableStateOf(NewsState())
    val NewsDataStateCricket = _NewsDataStateCricket
    private val _NewsDataStateFootball = mutableStateOf(NewsState())
    val NewsDataStateFootball = _NewsDataStateFootball


    init {
        fetchNewsData(_NewsDataState,"Palghar")
        fetchNewsData(_NewsDataStateCricket,"cricket")
        fetchNewsData(_NewsDataStateFootball,"football")

    }


    private fun fetchNewsData(_NewsDataState : MutableState<NewsState>, query: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = recipientService.getAllNews(query)
                if (response.isSuccessful) {
                    val data = response.body()
                    _NewsDataState.value = _NewsDataState.value.copy(
                        loading = false,
                        error = null,
                        // Pass the whole list of articles (or emptyList() if data is null)
                        list = data?.articles ?: emptyList()

                    )
                    Log.e( "NewsData", "Response : ${NewsDataState.value.list[2].title}")

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