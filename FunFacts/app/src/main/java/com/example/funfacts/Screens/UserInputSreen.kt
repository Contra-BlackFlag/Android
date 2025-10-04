package com.example.funfacts.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ShouldPauseCallback
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.Pair
import androidx.navigation.NavController
import com.example.funfacts.AnimalCard
import com.example.funfacts.ButtonComponent
import com.example.funfacts.R
import com.example.funfacts.TextComponent
import com.example.funfacts.TextFieldComponent
import com.example.funfacts.TopBar
import com.example.funfacts.UserDataUiEvents
import com.example.funfacts.UserInputViewModel


@Composable
fun UserInputScreen(ViewModelUserInput: UserInputViewModel,GotoWelcomeScreen : (valuesPair : Pair<String, String>) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxSize()
            .padding(18.dp)) {
            TopBar("Hi there \uD83D\uDE0A")

            Spacer(modifier = Modifier.size(20.dp))

            TextComponent(
                "Let's learn about U!",
                24.sp,
                    )

            Spacer(modifier = Modifier.size(20.dp))

            TextComponent(
                "This app will prepare a details page based on input you provide",
                18.sp,
            )
            Spacer(modifier = Modifier.size(60.dp))
            TextComponent("Name",
                18.sp)
            Spacer(modifier = Modifier.size(10.dp))
            TextFieldComponent(
                onTextChanged = {
                   ViewModelUserInput.onEvent(
                       UserDataUiEvents.UserNameEntered(it)
                   )
                }
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextComponent("What do you like ?",
                18.sp)
            Row {
                AnimalCard(R.drawable.cat,
                    Color.White,
                    AnimalSelected = {
                        ViewModelUserInput.onEvent(
                            UserDataUiEvents.AnimalSelected(it)
                    )},
                    selected =  ViewModelUserInput.uiState.value.animalSelected == "Cat"
                    )
                AnimalCard(R.drawable.dog,Color(0xFFE7A555),
                    AnimalSelected = {
                        ViewModelUserInput.onEvent(
                            UserDataUiEvents.AnimalSelected(it)
                        )},
                    selected =  ViewModelUserInput.uiState.value.animalSelected == "Dog")
            }
            Spacer(modifier = Modifier.weight(1f))
            if (!ViewModelUserInput.uiState.value.animalSelected.isNullOrEmpty()
                && !ViewModelUserInput.uiState.value.nameEntered.isNullOrEmpty()){

                ButtonComponent( {
                        GotoWelcomeScreen(
                            Pair(
                                ViewModelUserInput.uiState.value.nameEntered,
                                ViewModelUserInput.uiState.value.animalSelected
                            )
                        )
                })
            }

        }
    }
}

//@SuppressLint("ViewModelConstructorInComposable","NavControllerConstructorInComposable")
//@Composable
//@Preview(showBackground = true)
//fun UserInputScreenPreview(){
//    UserInputScreen(UserInputViewModel(), NavController)
//}