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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
                    MyMapComposeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


class MyMapViewModel : ViewModel() {

    // This provider will fetch map tiles from the OpenStreetMap server
    private val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
        try {
            // This is the standard URL format for OSM tiles
            val url = "https://a.tile.openstreetmap.org/$zoomLvl/$col/$row.png"
            URL(url).openStream()
        } catch (e: Exception) {
            // Return null if a tile can't be fetched
            null
        }
    }

    // This is the main state object for the map
    val state = MapState(
        levelCount = 18,        // The number of zoom levels
        fullWidth = 4096,       // The full map width and height at zoom level 0
        fullHeight = 4096
    ).apply {
        // Add the OSM tile provider as a layer
        addLayer(tileStreamProvider)
        // Optionally enable map rotation gestures
        enableRotation()

        // Center the map on Nashik (using a calculation to get the x, y coordinates)
        // Note: You'll likely need a helper function to convert Lat/Lng to pixel coordinates.
        // For now, this is a placeholder to show how it works.
//        scrollTo(2225, 1550, 1f)
    }
}

@Composable
fun MyMapComposeScreen(
    modifier: Modifier = Modifier,
    viewModel: MyMapViewModel = viewModel()
) {
    MapUI(
        modifier = modifier.fillMaxSize(),
        state = viewModel.state
    )
}
