package com.example.newswithscroll

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newswithscroll.Screen.FEED
import com.example.newswithscroll.Screen.LANDINGPAGE
import com.example.newswithscroll.Viewmodel.MainViewModel

@Composable
fun Navigation(modifier: PaddingValues){
    val viewmodel : MainViewModel = viewModel()
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = Screens.LANDINGPAGE){
        composable(Screens.LANDINGPAGE){
            LANDINGPAGE(modifier, navController)
        }
        composable(Screens.FEED){
            FEED(viewmodel, modifier)
        }
    }
}