package com.example.browser

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.browser.Screens.HomePage
import com.example.browser.Screens.Screens
import com.example.browser.Screens.WebView
import kotlinx.coroutines.launch

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
