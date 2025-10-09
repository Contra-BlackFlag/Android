package com.example.noteappfirebase.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteappfirebase.HomePage
import com.example.noteappfirebase.auth.authviewmodel.AuthViewModel
import com.example.noteappfirebase.note.NoteViewModel
import com.example.noteappfirebase.screens.Auth.LoginScreen
import com.example.noteappfirebase.screens.Auth.SignUpScreen
import com.example.noteappfirebase.screens.NoteScreen
import com.example.noteappfirebase.screens.Screen

@Composable
fun NoteAppNav(modifier: Modifier = Modifier){
    val navHostController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val noteViewModel: NoteViewModel = viewModel()

    NavHost(navController = navHostController, startDestination = Screen.LOG_IN, modifier = modifier){
        composable (Screen.LOG_IN){
            LoginScreen(navHostController = navHostController, viewModel = authViewModel)
        }
        composable (Screen.SIGN_UP){
            SignUpScreen(navHostController = navHostController, viewModel = authViewModel)
        }
        composable (Screen.NOTES){
            NoteScreen(viewModel = noteViewModel, navController = navHostController)
        }
        composable(Screen.HOME_PAGE) {
            HomePage(noteViewModel)
        }
    }
}