package com.example.noteappfirebase.screens

import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.noteappfirebase.model.Note
import com.example.noteappfirebase.note.NoteViewModel

@Composable
fun NoteScreen(modifier: Modifier = Modifier, viewModel: NoteViewModel,navController: NavController){
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.addNote(Note(title = title, description = desc), imageUri = it){
            title = "";desc = ""
            Toast.makeText(context,"Note Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    Column (modifier = Modifier.padding(16.dp)){
        TextField(value = title, onValueChange = {
            title = it }, label = { Text("Title") }
        )

        TextField(value = desc, onValueChange = {
           desc = it }, label = { Text("Description") }
        )

        Button(onClick = {launcher.launch("image/*")
                           navController.navigate(Screen.HOME_PAGE) }) {
            Text("Upload Image and Save")
        }

    }
}