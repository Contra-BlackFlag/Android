package com.example.funfacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.funfacts.Screens.WelcomeScreen

@Composable
fun TopBar(value : String){
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(value,
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium)
        Image(painter = painterResource(R.drawable.image),
            contentDescription = "Profile icon",
            modifier = Modifier
                .size(50.dp)
                .padding(end = 10.dp))

    }
}
@Composable
fun TextComponent(textValue : String, textSize : TextUnit, color: Color = Color.Black){
        Text(textValue,
            fontSize = textSize,
            color = color,
            fontWeight = FontWeight.Light)
}

@Composable
fun TextFieldComponent(
    onTextChanged : (name : String) -> Unit
){
    val localFocusManager = LocalFocusManager.current
    var currentValue by remember { mutableStateOf("") }
    OutlinedTextField(value = currentValue,
        onValueChange = {
            currentValue = it
                onTextChanged(it)
        },
        placeholder = {
            Text("Enter your Name", fontSize = 18.sp)
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle.Default.copy(fontSize = 24.sp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        })
}

@Composable
fun AnimalCard(AnimalName : Int, ColorOftheCard : Color, selected : Boolean,AnimalSelected : (animalName:String) -> Unit){
    Card (colors = CardDefaults.cardColors(ColorOftheCard),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(24.dp)
            .size(130.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ){
        val localFocusManager = LocalFocusManager.current

        Box(
             modifier = Modifier
                 .fillMaxSize()
                 .border(
                     width = 2.dp,
                     color = if (selected) Color.Green else Color.Transparent,
                     shape = RoundedCornerShape(8.dp)
                 )

        ){
            Image(painter = painterResource(id = AnimalName),
                "Animal Image of Dog",
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .clickable{
                        val name = if (AnimalName == R.drawable.cat) "Cat" else "Dog"
                        AnimalSelected(name)
                        localFocusManager.clearFocus()
                    }
            )
        }
    }
}

@Composable
fun ButtonComponent(
    goToDetailScreen : () -> Unit
){
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { goToDetailScreen() }
    ) {
        TextComponent("Go to details Screen",18.sp,Color.White)
    }
}

@Composable
fun TextWithShadow(value : String){
    val shadowOffset = Offset(x = 1f, y = 2f)
    Text(text = value,
        fontSize = 24.sp,
        fontWeight = FontWeight.Light,
        style = TextStyle(shadow = Shadow(Utility.generateColor(),
            shadowOffset,
            2f,),
            fontFamily = FontFamily.Serif
        )
    )

}

@Composable
fun FactComposable(value: String){
        Card (modifier = Modifier.padding(32.dp).fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)){
            Column (modifier = Modifier.padding(10.dp)){
                Image(painter = painterResource(R.drawable.quote),
                    "Quote Image",
                    modifier = Modifier.rotate(180f))
                Spacer(modifier = Modifier.size(24.dp))
                TextWithShadow(value)
                Spacer(modifier = Modifier.size(24.dp))
                Image(painter = painterResource(R.drawable.quote),
                    "Quote Image",
                )
            }
        }
}
@Composable
@Preview(showBackground = true)
fun FactComposablePreview(){
   FactComposable("Piyush")
}
//
//@Composable
//@Preview(showBackground = true)
//fun AnimalCardPreview(){
//    AnimalCard(R.drawable.cat,
//        Color.White,
//        true,{})
//}
//@Composable
//@Preview(showBackground = true)
//fun TextFieldComponentPreview(){
//    TextFieldComponent({})
//}
//
//@Composable
//@Preview(showBackground = true)
//fun TextComponentPreview(){
//    TextComponent("Piyush is a gentleman",50.sp,Color.Black)
//}
//
//
//@Composable
//@Preview(showBackground = true)
//fun TopBarPreview(){
//    TopBar("")
//}
