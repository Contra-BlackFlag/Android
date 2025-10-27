package com.example.liquidglassprojejct.Screens

import android.annotation.SuppressLint
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mrtdk.glass.GlassContainer
import com.example.liquidglassprojejct.R
import com.example.liquidglassprojejct.navigation
import com.mrtdk.glass.GlassBox
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen(navigation : ()-> Unit) {
    Scaffold {

            GlassContainer(
                content = {
                    Image(painter = painterResource(R.drawable.m),
                        "",
                        modifier = Modifier
                        .fillMaxSize()
                    )
                },
                modifier = Modifier
                    .padding()
                    .fillMaxSize()
            ) {
                GlassBox(
                    modifier = Modifier.align(
                        Alignment.Center
                    ).size(100.dp),
                    blur = 0.5f,
                    scale = 0.3f,
                    shape = RoundedCornerShape(size = 20.dp),
                    warpEdges = 0.3f,
                    contentAlignment = Alignment.Center,
                    elevation = 10.dp
                ) {
                    Image(painter = painterResource(R.drawable.note)
                        ,"",
                        modifier = Modifier.fillMaxSize())
                }
            }
        LaunchedEffect(Unit) {
            delay(500)
            navigation()
        }

    }
}