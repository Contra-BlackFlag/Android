package com.example.wishlistapp

// Removed: import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues // Added
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
// import androidx.compose.foundation.layout.wrapContentSize // wrapContentSize on Column was changed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
// M3 imports
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text // Replaces androidx.wear.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wishlistapp.Data.Wish
import kotlinx.coroutines.launch


// Removed M2 and Wear imports:
// import androidx.compose.material.OutlinedTextField
// import androidx.compose.material.TextFieldDefaults
// import androidx.compose.wear.compose.material.Text


@Composable
fun AddEditDetailsView(
    id : Long,
    viewModel: WishViewModel,
    navController: NavController
){
    val snackMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    if (id != 0L){
        val wish = viewModel.getAWishByID(id).collectAsState(Wish(0L,"",""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description

    }
    else {
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }
    Scaffold(scaffoldState = scaffoldState,
        topBar = {
        AppBarView(
            title = if (id != 0L) "Update Wish" else "Add Wish", // Changed "UpdateList" to "Update Wish" for consistency
            onBackNavClicked = {
                navController.navigate(Screen.HomeScreen.route)
            })
    }) { paddingValues: PaddingValues -> // Use paddingValues from Scaffold
        Column(
            modifier = Modifier
                .padding(paddingValues) // Apply padding from Scaffold
                .fillMaxWidth(), // Column takes full width
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
                WishTextField("Title",value = viewModel.wishTitleState, onValueChanged = {
                    viewModel.onWishTitleChange(it) })
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField("Description",value = viewModel.wishDescriptionState, onValueChanged = {
                viewModel.onWishDescriptionChange(it) })
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                    if (viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()){
                        if (id != 0L){
                            viewModel.updateAWish(
                                Wish(
                                    id = id,
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )
                            snackMessage.value = "Wish is Updated"
                        }
                        else{
                                viewModel.addAWish(
                                    Wish(title = viewModel.wishTitleState.trim(),
                                        description = viewModel.wishDescriptionState.trim())
                                )
                            snackMessage.value = "Wish is Added"
                        }

                    }
                else{
                    snackMessage.value = "Enter fields to create a wish"
                }
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }

            }) {
                Text(
                    text = if (id!= 0L) "Update Wish" else "Add Wish",
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }
    }
}

@Composable
fun WishTextField(
    label:String,
    value:String,
    onValueChanged : (String) -> Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged, // Parameter name is onValueChange for the composable
        modifier = Modifier.fillMaxWidth(), // Original modifier
        label = { Text(text = label, color = Color.Black) }, // label is a composable lambda
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors( // Use M3 defaults
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        )
    )
}

@Preview(showBackground = true)
@Composable
fun WishTextFieldPreview(){
    WishTextField("text","text",{})
}