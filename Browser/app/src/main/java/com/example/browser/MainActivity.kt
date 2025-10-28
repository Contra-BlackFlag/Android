package com.example.browser

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import com.example.browser.Screens.HomePage
import com.example.browser.ui.theme.BrowserTheme
import java.net.UnknownHostException

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BrowserTheme {
                val viewModel : MainViewModel = MainViewModel()
                Navigation(viewModel)
            }
        }
    }
}

