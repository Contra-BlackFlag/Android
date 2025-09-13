package com.example.captaindirection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.captaindirection.ui.theme.CaptainDirectionTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaptainDirectionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CaptainGame()
                }
            }
        }
    }
}

@Composable
fun CaptainGame(){
    val treasuresFound = remember{ mutableStateOf(0) }
    val direction =  remember{ mutableStateOf("North") }
    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        Text(text = "Treasure Found ${treasuresFound.value}")
        Text(text = "Current Direction ${direction.value}")
        Button(onClick = { direction.value = "East"
            if (Random.nextBoolean()){
                treasuresFound.value += 1
            }}) {
            Text(text = "sail East")

        }
        Button(onClick = { direction.value = "West"
            if (Random.nextBoolean()){
                treasuresFound.value += 1
            }}) {
            Text(text = "sail West")

        }
        Button(onClick = { direction.value = "North"
            if (Random.nextBoolean()){
                treasuresFound.value += 1
            }}) {
            Text(text = "sail North")

        }
        Button(onClick = { direction.value = "South"
            if (Random.nextBoolean()){
                treasuresFound.value += 1
            }}) {
            Text(text = "sail South")

        }
    }
}

@Preview(showBackground = false)
@Composable
fun CaptainGamePreview(){
    CaptainGame()
}