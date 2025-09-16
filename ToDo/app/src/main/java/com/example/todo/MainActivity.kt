package com.example.todo

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
import androidx.navigation.Navigation
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.ShoppingItemList
import com.example.shoppinglist.ShoppingList
import com.example.todo.ui.theme.ToDoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTheme {
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
