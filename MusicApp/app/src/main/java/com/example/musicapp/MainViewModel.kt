package com.example.musicapp


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _currentScreen : MutableState<Screen> = mutableStateOf(Screen.DrawerScreen.Addaccount)
    val currentScreen : MutableState<Screen>
        get() = _currentScreen

    fun setcurrentScreen(screen: Screen){
        _currentScreen.value = screen
    }

}