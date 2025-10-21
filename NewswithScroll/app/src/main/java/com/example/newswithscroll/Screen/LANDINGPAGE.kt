package com.example.newswithscroll.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import com.example.newswithscroll.Screens


@Composable
fun LANDINGPAGE(innerpadding: PaddingValues, navController: NavController){
    Column (modifier = Modifier.fillMaxSize().padding(innerpadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){

        Text("HI", fontFamily = FontFamily.SansSerif)
        Button(onClick = {
            navController.navigate(Screens.FEED)
            }
         ) {
                Text("Go TO Feed")
        }
    }
}