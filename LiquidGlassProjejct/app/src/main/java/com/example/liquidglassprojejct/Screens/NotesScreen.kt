package com.example.liquidglassprojejct.Screens

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberTopAppBarState
import com.example.liquidglassprojejct.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.liquidglassprojejct.Data.Notes
import com.mrtdk.glass.GlassBox
import com.mrtdk.glass.GlassBoxScope
import com.mrtdk.glass.GlassContainer
import com.mrtdk.glass.GlassScope
import kotlin.collections.mutableListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(navigate : ()-> Unit) {
   Scaffold(
       topBar = {
           TopAppBar(
               title = {
                   Text("Notes", fontFamily = FontFamily.SansSerif)
               },
               modifier = Modifier.fillMaxWidth()

           )
       }
   ) {
       GlassContainer(
           content = { Notes(it) },
           modifier = Modifier
               .padding()
               .fillMaxSize()
       ) {
           GlassBox(
               modifier = Modifier
                   .padding(bottom = 50.dp)
                   .padding(horizontal = 25.dp)
                   .align(Alignment.BottomEnd)
                   .size(75.dp).clickable(
                       onClick = {
                           navigate()
                       }
                   ),
               blur = 0.5f,
               scale = 0.3f,
               shape = RoundedCornerShape(size = 20.dp),
               warpEdges = 0.3f,
               contentAlignment = Alignment.Center,
               elevation = 10.dp
           ) {
              Box(
                  modifier = Modifier
              ){
                  Icon(painter = painterResource(R.drawable.add),"")
              }
           }

       }
   }



}
@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun Notes(paddingValues: PaddingValues){

    GlassContainer(
        content = {
            Image(painter = painterResource(R.drawable.m),"", modifier = Modifier.fillMaxSize())
        },
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(noteslist){
                GlassBox(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(width = 400.dp, height = 100.dp)
                        .align(Alignment.Center),
                    blur = 0.5f,
                    scale = 0.2f,
                    shape = RoundedCornerShape(size = 20.dp),
                    warpEdges = 0.3f
                ) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ){
                        Icon(imageVector = Icons.Default.MoreVert,
                            "",
                            tint = Color.White,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                        Box(modifier = Modifier.size(width = 300.dp, height = 70.dp)){
                            Column (){


                                Text(it.title, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(it.content, color = Color.White)
                            }
                        }
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotetoList() {
Scaffold (
    topBar = {
        TopAppBar(
            title = {
                Text("Notes", fontFamily = FontFamily.SansSerif)
            },
            modifier = Modifier.fillMaxWidth()

        )
    }
){

    GlassContainer(
        content = {
            AddNotes(it)
        },
        modifier = Modifier
            .padding()
            .fillMaxSize()
    ) {
        GlassBox(
            modifier = Modifier
                .padding(bottom = 50.dp)
                .padding(horizontal = 25.dp)
                .align(Alignment.BottomEnd)
                .size(75.dp).clickable(
                    onClick = {

                    }
                ),
            blur = 0.5f,
            scale = 0.3f,
            shape = RoundedCornerShape(size = 20.dp),
            warpEdges = 0.3f,
            contentAlignment = Alignment.Center,
            elevation = 10.dp
        ) {
            Box(
                modifier = Modifier
            ){
                Icon(painter = painterResource(R.drawable.check_24),"")
            }
        }

    }
}
}
@Composable
fun AddNotes(paddingValues: PaddingValues) {
    var title by remember { mutableStateOf("note.title") }
    var content by remember { mutableStateOf("note.content") }

         GlassContainer(
             content = {
                 Image(painter = painterResource(R.drawable.m),"", modifier = Modifier.fillMaxSize())

             },
             modifier = Modifier
                 .padding()
                 .fillMaxSize()
         ) {
             GlassBox(
                 modifier = Modifier
                     .padding(bottom = 520.dp)
                     .padding(horizontal = 25.dp)
                     .align(Alignment.Center).fillMaxWidth()
                     .size(100.dp),
                 blur = 0.5f,
                 scale = 0.3f,
                 shape = RoundedCornerShape(size = 20.dp),
                 warpEdges = 0.3f,
                 contentAlignment = Alignment.Center,
                 elevation = 10.dp
             ){
                 Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(100.dp)

            ) {
                TextField(label = {
                    Text("Title : ", color = Color.White)
                },
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldColors(

                        // Text colors
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.Gray,
                        disabledTextColor = Color.Gray,
                        errorTextColor = Color.Red,

                        // Container colors
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color(0xFFE0E0E0),
                        errorContainerColor = Color(0xFFFFEBEE),

                        // Cursor colors
                        cursorColor = Color.White,
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



            }

             }
             GlassBox(
                 modifier = Modifier
                     .padding(bottom = 50.dp)
                     .padding(horizontal = 25.dp)
                     .align(Alignment.Center).fillMaxWidth()
                     .size(350.dp),
                 blur = 0.3f,
                 scale = 0.1f,
                 shape = RoundedCornerShape(size = 20.dp),
                 warpEdges = 0.2f,
                 contentAlignment = Alignment.Center,
                 elevation = 10.dp
             ){
                 Box(
                     modifier = Modifier
                         .fillMaxSize()

                 ) {
                     OutlinedTextField(
                         label = {
                             Text("Content : ", color = Color.White, modifier = Modifier.padding(top = 5.dp ))
                         },
                         value = content,
                         onValueChange = {
                             content = it
                         },
                         shape = RoundedCornerShape(20.dp),
                         modifier = Modifier.fillMaxSize(),
                         colors = TextFieldColors(

                             // Text colors
                             focusedTextColor = Color.White,
                             unfocusedTextColor = Color.Gray,
                             disabledTextColor = Color.Gray,
                             errorTextColor = Color.Red,

                             // Container colors
                             focusedContainerColor = Color.Transparent,
                             unfocusedContainerColor = Color.Transparent,
                             disabledContainerColor = Color(0xFFE0E0E0),
                             errorContainerColor = Color(0xFFFFEBEE),

                             // Cursor colors
                             cursorColor = Color.White,
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



                 }
             }

         }
    }

@Composable
fun EditNote(note: Notes,
             onUpdate : (Notes) -> Unit,
             onCancel : () -> Unit) {
    val name = remember { mutableStateOf("") }
    val content = remember { mutableStateOf("") }

    GlassContainer(
        content = {
            Image(painter = painterResource(R.drawable.m), "", modifier = Modifier.fillMaxSize())

        },
        modifier = Modifier
            .padding()
            .fillMaxSize()
    ) {
        GlassBox(
            modifier = Modifier
                .padding(bottom = 520.dp)
                .padding(horizontal = 25.dp)
                .align(Alignment.Center).fillMaxWidth()
                .size(100.dp),
            blur = 0.5f,
            scale = 0.3f,
            shape = RoundedCornerShape(size = 20.dp),
            warpEdges = 0.3f,
            contentAlignment = Alignment.Center,
            elevation = 10.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(100.dp)

            ) {
                TextField(
                    label = {
                        Text("Title : ", color = Color.White)
                    },
                    value = name.value,
                    onValueChange = {
                        name.value = it
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldColors(

                        // Text colors
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.Gray,
                        disabledTextColor = Color.Gray,
                        errorTextColor = Color.Red,

                        // Container colors
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color(0xFFE0E0E0),
                        errorContainerColor = Color(0xFFFFEBEE),

                        // Cursor colors
                        cursorColor = Color.White,
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


            }

        }
        GlassBox(
            modifier = Modifier
                .padding(bottom = 50.dp)
                .padding(horizontal = 25.dp)
                .align(Alignment.Center).fillMaxWidth()
                .size(350.dp),
            blur = 0.3f,
            scale = 0.1f,
            shape = RoundedCornerShape(size = 20.dp),
            warpEdges = 0.2f,
            contentAlignment = Alignment.Center,
            elevation = 10.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                OutlinedTextField(
                    label = {
                        Text("Content : ", color = Color.White)
                    },
                    value = content.value,
                    onValueChange = {
                        content.value = it
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxSize(),
                    colors = TextFieldColors(

                        // Text colors
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.Gray,
                        disabledTextColor = Color.Gray,
                        errorTextColor = Color.Red,

                        // Container colors
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color(0xFFE0E0E0),
                        errorContainerColor = Color(0xFFFFEBEE),

                        // Cursor colors
                        cursorColor = Color.White,
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


            }
        }

    }
}



