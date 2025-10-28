package com.example.browser.Screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.browser.MainViewModel
import com.example.browser.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun HomePage(NavController : NavController,viewModel: MainViewModel) {
   

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Google",
                modifier = Modifier
                    .padding(top = 32.dp)
                    .size(120.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))


                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(55) // 👈 Pill shape
                        )
                        .clip(RoundedCornerShape(55)),
                    contentAlignment = Alignment.Center

                ){
                    Row (
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        TextField(
                            value = viewModel.url.value,
                            onValueChange = {
                                viewModel.urlrequest(it)
                            },
                            placeholder = { Text("Enter URL") },
                            modifier = Modifier
                                .width(250.dp)
                                .height(56.dp).padding(top = 5.dp, start =7.dp),
                            shape = RoundedCornerShape(25.dp),
                            singleLine = true,
                            colors = TextFieldColors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Gray,
                                disabledTextColor = Color.Gray,
                                errorTextColor = Color.Red,

                                // Container colors
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color(0xFFE0E0E0),
                                errorContainerColor = Color(0xFFFFEBEE),

                                // Cursor colors
                                cursorColor = Color.Black,
                                errorCursorColor = Color.Red,

                                // Selection colors
                                textSelectionColors = TextSelectionColors(
                                    handleColor = Color.Black,
                                    backgroundColor = Color(0x22000000)
                                ),

                                // Indicator colors
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,

                                // Leading icon colors
                                focusedLeadingIconColor = Color.Transparent,
                                unfocusedLeadingIconColor = Color.Transparent,
                                disabledLeadingIconColor = Color.Transparent,
                                errorLeadingIconColor = Color.Transparent,

                                // Trailing icon colors
                                focusedTrailingIconColor = Color.Transparent,
                                unfocusedTrailingIconColor = Color.Transparent,
                                disabledTrailingIconColor = Color.Transparent,
                                errorTrailingIconColor = Color.Transparent,

                                // Label colors
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Gray,
                                disabledLabelColor = Color.LightGray,
                                errorLabelColor = Color.Red,

                                // Placeholder colors
                                focusedPlaceholderColor = Color.Transparent,
                                unfocusedPlaceholderColor = Color.Transparent,
                                disabledPlaceholderColor = Color.Transparent,
                                errorPlaceholderColor = Color.Transparent,

                                // Supporting text colors
                                focusedSupportingTextColor = Color.Gray,
                                unfocusedSupportingTextColor = Color.Gray,
                                disabledSupportingTextColor = Color.LightGray,
                                errorSupportingTextColor = Color.Red,

                                // Prefix & Suffix colors
                                focusedPrefixColor = Color.Black,
                                unfocusedPrefixColor = Color.DarkGray,
                                disabledPrefixColor = Color.Gray,
                                errorPrefixColor = Color.Red,

                                focusedSuffixColor = Color.Black,
                                unfocusedSuffixColor = Color.DarkGray,
                                disabledSuffixColor = Color.Gray,
                                errorSuffixColor = Color.Red
                            )

                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Button(
                            onClick = {
                                NavController.navigate(Screens.WEBVIEW)
                            },
                            colors = ButtonColors(
                                containerColor = Color.White,
                                contentColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent
                            ),
                            shape = CircleShape
                        ){
                            Image(painter = painterResource(R.drawable.search),
                                "search",
                                modifier = Modifier.size(56.dp)
                            )
                        }
                }


            }
        }
    }
}






