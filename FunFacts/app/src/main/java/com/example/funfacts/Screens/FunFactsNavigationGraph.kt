package com.example.funfacts.Screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.funfacts.UserInputViewModel

@Composable
fun FunFactsNavigationGraph(userInputViewModel: UserInputViewModel = viewModel()){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = Routes.UserInputScreen){
        composable(Routes.UserInputScreen){
            UserInputScreen( userInputViewModel,
                {
                    navController.navigate(Routes.WelcomeScreen+"/${it.first}/${it.second}")
                }
            )
        }
        composable("${Routes.WelcomeScreen}/{${Routes.USER_NAME}}/{${Routes.ANIMAL_SELECTED}}",
            arguments = listOf(
                navArgument(name = Routes.USER_NAME){type = NavType.StringType},
                navArgument(name = Routes.ANIMAL_SELECTED){type = NavType.StringType}

                )
        ){
            val username = it?.arguments?.getString(Routes.USER_NAME)
            val animalname = it?.arguments?.getString(Routes.ANIMAL_SELECTED)
            WelcomeScreen(username,animalname,navController)
        }
    }
}