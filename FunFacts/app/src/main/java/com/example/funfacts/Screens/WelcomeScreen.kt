package com.example.funfacts.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.funfacts.FactComposable
import com.example.funfacts.FactsViewModel
import com.example.funfacts.TextComponent
import com.example.funfacts.TextWithShadow
import com.example.funfacts.TopBar

@Composable
fun WelcomeScreen(UserName: String?, AnimalName: String?, navController: NavHostController){
    Surface(
        modifier = Modifier.fillMaxWidth()

    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)){
            TopBar(value = "Welcome ${UserName} \uD83D\uDE0D")
            Spacer(modifier = Modifier.size(20.dp))
            TextComponent("Thank you for sharing you data ${UserName?.lowercase()}!",
                20.sp,)
            Spacer(Modifier.size(60.dp))
            val finalText = if (AnimalName == "Cat") "You like Cats \uD83D\uDC31" else "You like Dogs \uD83D\uDC36"
            TextWithShadow(finalText)

            val FactsViewModel : FactsViewModel = viewModel()
            var text = remember { mutableStateOf(FactsViewModel.generateRandomFact(AnimalName!!)) }
            Box(modifier = Modifier.clickable{
                text.value =FactsViewModel.generateRandomFact(AnimalName!!)
            }){
                FactComposable(
                    value = text.value
                )
            }




        }
    }
}
@Composable
fun ReloadFacts(){
    Button(onClick = {

    }) {
        Text(
            "New Fact"
        )
    }
}
