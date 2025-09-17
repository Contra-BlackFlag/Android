package com.example.safewayapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sih.ui.theme.SIHTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafetyZonesMapScreen(navController: NavController) {
    // --- Sample Data for Geo-fenced Zones ---
    // In a real app, this data would come from your backend.
    val kyotoStationArea = LatLng(34.9858, 135.7588)
    val gionDistrictCautionZone = listOf(
        LatLng(35.004, 135.772), LatLng(35.005, 135.780),
        LatLng(35.002, 135.781), LatLng(35.001, 135.773)
    )
    val arashiyamaSafeZone = listOf(
        LatLng(35.015, 135.675), LatLng(35.017, 135.680),
        LatLng(35.013, 135.682), LatLng(35.012, 135.676)
    )
    val highRiskZone = listOf( // Small, specific area for demonstration
        LatLng(34.995, 135.765), LatLng(34.996, 135.766),
        LatLng(34.994, 135.767)
    )

    // Set the initial camera position to our sample location
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(kyotoStationArea, 13f) // Zoom level 13
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("SAFETY ZONES MAP", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Handle filtering zones */ }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Apply padding from Scaffold
            cameraPositionState = cameraPositionState
        ) {
            // --- Draw the Polygons on the Map ---

            // High-Risk Zone (Red)
            Polygon(
                points = highRiskZone,
                fillColor = Color.Red.copy(alpha = 0.3f),
                strokeColor = Color.Red,
                strokeWidth = 5f,
                clickable = true,
                onClick = { /* TODO: Show info for high-risk zone */ }
            )

            // Caution Zone (Yellow)
            Polygon(
                points = gionDistrictCautionZone,
                fillColor = Color.Yellow.copy(alpha = 0.3f),
                strokeColor = Color.Yellow,
                strokeWidth = 5f,
                clickable = true,
                onClick = { /* TODO: Show info for caution zone */ }
            )

            // Safe Zone (Green)
            Polygon(
                points = arashiyamaSafeZone,
                fillColor = Color.Green.copy(alpha = 0.3f),
                strokeColor = Color.Green,
                strokeWidth = 5f,
                clickable = true,
                onClick = { /* TODO: Show info for safe zone */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SafetyZonesMapScreenPreview() {
    SIHTheme {
        // Note: The map itself will not render in the Android Studio preview window.
        // You must run the app on an emulator or a real device to see it.
        SafetyZonesMapScreen(rememberNavController())
    }
}