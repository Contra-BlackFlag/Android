package com.example.shoppinglis

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglis.ui.theme.ShoppingLisTheme
import com.example.todo.LocationSelectionScreen
import com.example.todo.LocationUtil
import com.example.todo.LocationViewModel
import com.example.todo.ShoppingList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingLisTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation(){
    val navController  = rememberNavController()
    val viewModel : LocationViewModel = viewModel()
    val context = LocalContext.current
    val locationUtils = LocationUtil(context)

    NavHost(navController,startDestination = "shopppinglistscreen"){
        composable("shoppinglistscreen"){
            ShoppingList(
                locationUtils = locationUtils,
                viewModel = viewModel,
                context = context,
                navController = navController,
                address = viewModel.address.value.firstOrNull()?.formatted_address?: "No address"
            )
        }
        dialog("locationscreen") {backStackEntry ->
            viewModel.location.value?.let {
                LocationSelectionScreen(it, onLocationSelected = {
                    viewModel.fetch("${it.latitude},${it.longitude}")
                    navController.popBackStack()
                })
            }

        }
    }



}