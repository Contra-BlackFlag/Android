package com.example.browser

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.browser.Screens.HomePage
import com.example.browser.Screens.Screens

@Composable
fun Navigation(viewModel: MainViewModel) {
    val NavController = rememberNavController()

    NavHost(navController = NavController, startDestination = Screens.HOMEPAGE){
        composable(Screens.HOMEPAGE) {
            HomePage(NavController,viewModel)
        }
        composable(Screens.WEBVIEW) {
            WebView(NavController,viewModel)
        }
    }
}