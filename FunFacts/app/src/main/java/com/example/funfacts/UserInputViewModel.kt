package com.example.funfacts

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.funfacts.Screens.UserInputScreen

class UserInputViewModel : ViewModel() {
    companion object{
        const val TAG = "UserInputViewModel"
    }
    var uiState = mutableStateOf(UserInputScreenState())
    fun onEvent(events: UserDataUiEvents){
        when(events){
            is UserDataUiEvents.UserNameEntered -> {
                    uiState.value = uiState.value.copy(
                        nameEntered = events.name
                    )

                Log.d(TAG, "onEvent: UserNameEntered->>")
                Log.d(TAG, "${uiState.value}")
            }
            is UserDataUiEvents.AnimalSelected ->{
                        uiState.value = uiState.value.copy(
                            animalSelected = events.animalValue
                        )
                Log.d(TAG, "onEvent: AnimalSelected->>")
                Log.d(TAG, "${uiState.value}")
            }
        }
    }
}

data class UserInputScreenState(
    val nameEntered : String = "",
    val animalSelected : String = ""
)
sealed class UserDataUiEvents{
    data class UserNameEntered(val name : String) : UserDataUiEvents()
    data class AnimalSelected(val animalValue : String) : UserDataUiEvents()
}