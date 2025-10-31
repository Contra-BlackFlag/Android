package com.example.browser.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.browser.MainViewModel
import com.example.browser.Screens.History
import com.example.browser.Screens.HomePage
import com.example.browser.Screens.Screens
import com.example.browser.Screens.WebView

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
        composable(Screens.HISTORY) {
            History(viewModel,NavController)
        }
    }

}
