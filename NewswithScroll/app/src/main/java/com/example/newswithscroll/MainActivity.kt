package com.example.newswithscroll

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newswithscroll.Viewmodel.MainViewModel
import com.example.newswithscroll.ui.theme.NewswithScrollTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition", "ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewswithScrollTheme {
                val viewmodel : MainViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                 Navigation(innerPadding)
                }
            }
        }
    }
}

