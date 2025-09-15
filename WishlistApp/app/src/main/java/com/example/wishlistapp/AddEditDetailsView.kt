package com.example.wishlistapp

import android.R
import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

@Composable
fun AddEditDetailsView(
    id : Long,
    viewModel: WishViewModel,
    navController: NavController
){
    Scaffold(topBar =
        { AppBarView(title = if (id != 0L) "UpdateList" else "Add Wish" ,
            {}) }){
        Column(modifier = Modifier
            .padding(it)
            .wrapContentSize(),
           horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}
@Composable
fun WishTextField(
    label:String,
    value: String,
    onValueChanged : (String) -> Unit
){
  OutlinedTextField(
       value = value,
       onValueChanged = onValueChanged,
       label = { Text(text = label, color = R.color.black) },
       modifier = Modifier.fillMaxSize(),
       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
       
   )
}
