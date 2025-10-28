package com.example.liquidglassprojejct

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.liquidglassprojejct.Screens.AddNotes
import com.example.liquidglassprojejct.Screens.AddNotetoList
import com.example.liquidglassprojejct.Screens.Notes
import com.example.liquidglassprojejct.Screens.NotesScreen
import com.example.liquidglassprojejct.Screens.SplashScreen
import com.example.liquidglassprojejct.ui.theme.LiquidGlassProjejctTheme
import com.mrtdk.glass.GlassBox
import com.mrtdk.glass.GlassContainer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiquidGlassProjejctTheme {
                // In your MainActivity or Application class

                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    navigation()
                }
            }
        }
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splashscreen"){
        composable("splashscreen") {
            SplashScreen(
                {
                navController.navigate("home"){
                    popUpTo("splashscreen"){
                        inclusive = true
                    }
                }
                }
            )
        }
        composable("home") {
            NotesScreen(
                {
                    navController.navigate("addtolist")
                }
            )
        }
        composable("addtolist") {
            AddNotetoList()
        }
    }
}