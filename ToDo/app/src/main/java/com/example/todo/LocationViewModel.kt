package com.example.todo

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class LocationViewModel : ViewModel(){
    private val _location = mutableStateOf<LocationData?>(null)
    val location : State<LocationData?> = _location
    private val _address = mutableStateOf(listOf<GeoCodingResult>())
    val address : State<List<GeoCodingResult>> = _address

    fun updatelocation(newlocation: LocationData){
        _location.value = newlocation
    }

    fun fetch(latlng: String){
        try {
            viewModelScope.launch {
                    val result = RetrofitClient.create().getAddressfromCoordinate(
                        latlng,
                        
                    )
                    _address.value = result.result
            }
        }
        catch (e:Exception){
            Log.d("res1","${e.cause} ${e.message}")

        }
    }

}