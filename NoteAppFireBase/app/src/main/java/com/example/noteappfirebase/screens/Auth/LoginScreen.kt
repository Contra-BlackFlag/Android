package com.example.noteappfirebase.screens.Auth

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.noteappfirebase.auth.authviewmodel.AuthViewModel
import com.example.noteappfirebase.screens.Screen

@Composable
fun LoginScreen(modifier: Modifier = Modifier,viewModel: AuthViewModel,navHostController: NavHostController){
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        TextField(value = email, onValueChange = {
            email = it
            },
        label = { Text("Email") }
        )
        Spacer(Modifier.height(10.dp))
        TextField(value = password, onValueChange = {
            password = it
        },
            label = { Text("Password") }
        )

        Button(onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    viewModel.login(email, password) { success, message ->
                        if (success) {
                            navHostController.navigate(Screen.NOTES)
                        } else {
                            Toast.makeText(context, message ?: "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Login")
        }
        TextButton(onClick = {
            navHostController.navigate(Screen.SIGN_UP)
             }
        ) {
                Text("Don't Have Account? Sign Up")
        }
    }

}