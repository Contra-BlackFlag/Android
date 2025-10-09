package com.example.noteappfirebase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.noteappfirebase.note.NoteViewModel

@Composable
fun HomePage(viewModel : NoteViewModel){
    Column (modifier = Modifier.padding(16.dp)){

        LazyColumn() {
            items(viewModel.notes){
                Card (modifier = Modifier.padding(8.dp)){
                    Column (modifier = Modifier.padding(8.dp)){
                        Text(it.title, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(it.description)
                        if (it.imageUrl.isNotBlank()){
                            AsyncImage(model = it.imageUrl, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}