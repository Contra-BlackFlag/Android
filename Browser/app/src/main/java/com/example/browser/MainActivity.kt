package com.example.browser

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.example.browser.Navigation.Navigation
import com.example.browser.datastore.UserPreferences
import com.example.browser.ui.theme.BrowserTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            BrowserTheme {
                val viewModel : MainViewModel = MainViewModel(UserPreferences(context))
                Navigation(viewModel)
            }
        }
    }
}

