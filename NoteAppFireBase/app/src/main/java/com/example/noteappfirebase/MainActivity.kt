package com.example.noteappfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.noteappfirebase.navigation.NoteAppNav
import com.example.noteappfirebase.ui.theme.NoteAppFireBaseTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            NoteAppFireBaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NoteAppNav(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

