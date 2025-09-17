package com.example.sih

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.safewayapp.ui.screens.DigitalIdScreen
import com.example.safewayapp.ui.screens.SafetyZonesMapScreen
import com.example.sih.ui.theme.SIHTheme
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.api.scrollTo
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.state.MapState
import java.net.URL
import kotlin.math.PI
import kotlin.math.asinh
import kotlin.math.tan

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SIHTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController() // Create and remember our NavController
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}
object Route {
    const val HOME = "home"
    const val EMERGENCY = "emergency"
    const val DIGITAL_ID = "digital_id"
    const val SAFETY_ZONES_MAP = "safety_zones_map"
    const val ALERTS = "alerts" // Placeholder for a future screen
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.HOME, // The first screen the user sees
        modifier = modifier
    ) {
        composable(Route.HOME) {
            HomeScreen(navController = navController) // Pass navController to HomeScreen
        }
        composable(Route.EMERGENCY) {
            EmergencyScreen(navController = navController) // Pass navController to EmergencyScreen
        }
        composable(Route.DIGITAL_ID) {
            DigitalIdScreen(navController = navController) // Pass navController to DigitalIdScreen
        }
        composable(Route.SAFETY_ZONES_MAP) {
            SafetyZonesMapScreen(navController = navController) // Pass navController to SafetyZonesMapScreen
        }
        // TODO: Add composable for Route.ALERTS when that screen is built
    }
}

