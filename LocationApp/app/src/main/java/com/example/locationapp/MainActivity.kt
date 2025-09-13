package com.example.locationapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel : LocationViewModel = viewModel()
            LocationAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        App(viewModel)
                }
            }
        }
    }
}
@Composable
fun App(viewModel: LocationViewModel){
val context = LocalContext.current
    val LocationUtils = LocationUtil(context)
    LocationDisplay(LocationUtils,viewModel,context)
}

@Composable
fun LocationDisplay(
    locationUtil: LocationUtil,
    viewModel: LocationViewModel,
    context : Context
){
    val location = viewModel.location.value
    val address = location?.let {
        locationUtil.reverseGeocodeLocation(location)
    }
    val requestPermissionLauncher = rememberLauncherForActivityResult( contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION]  == true && permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true){


            }
            else{
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                if (rationaleRequired){
                    Toast.makeText(context,
                        "Location Needed",
                        Toast.LENGTH_LONG).show()
                }
                else {
                     Toast.makeText(context,
                        "Setting->More Networks->Location",
                        Toast.LENGTH_LONG).show()
                }
            }
        })
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (location != null){
            Text("${location.longitude} And ${location.Latitude}")
            Text("$address")
        }else Text("Location Not available")

        Button(onClick = {
            if (locationUtil.haslocationPermission(context)){
                locationUtil.requestLocationUpdates(viewModel)
            }
            else{
                requestPermissionLauncher.launch(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                )
            }
        }) {
            Text("Get Location")
        }

    }
}
