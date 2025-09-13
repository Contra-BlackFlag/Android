package com.example.aigallery

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // --- NEW: LiveData to hold all local images from the phone ---
    private val _localImages = MutableLiveData<List<Uri>>()
    val localImages: LiveData<List<Uri>> = _localImages

    // --- This LiveData will hold the filtered results from the server ---
    private val _searchResults = MutableLiveData<List<SearchResult>>()
    val searchResults: LiveData<List<SearchResult>> = _searchResults

    // --- This LiveData is for errors (unchanged) ---
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * NEW: Scans the device's MediaStore for all images.
     * This is run on a background thread using Dispatchers.IO.
     */
    fun loadLocalImages(contentResolver: ContentResolver) {
        viewModelScope.launch(Dispatchers.IO) {
            val imageUris = mutableListOf<Uri>()
            val projection = arrayOf(MediaStore.Images.Media._ID)
            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val contentUri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    )
                    imageUris.add(contentUri)
                }
            }
            _localImages.postValue(imageUris)
        }
    }

    /**
     * Performs a search using the backend server (unchanged).
     */
    fun performSearch(query: String) {
        viewModelScope.launch {
            try {
                val results = RetrofitClient.apiService.searchImages(query)
                _searchResults.postValue(results)
            } catch (e: Exception) {
                _error.postValue("An error occurred: ${e.message}")
            }
        }
    }

    /**
     * NEW: Clears the search results, causing the UI to revert
     * to showing all local images.
     */
    fun clearSearch() {
        _searchResults.postValue(emptyList())
    }
}