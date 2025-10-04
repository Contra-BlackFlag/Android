package com.example.musicapp

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

data class L(val title : String, val id : Int)


@Composable
fun Library(){
    val playlist = L("Playlist",R.drawable.playlist)
    val Album = L("Album",R.drawable.outline_album_24)
    val Artist = L("Artist",R.drawable.artist)
    val Genre = L("Genre",R.drawable.genre)
    val List = listOf<L>(playlist,Album,Artist,Genre)
    Column (modifier = Modifier.fillMaxSize()){
        LazyColumn {
            items(List){
                Lib(it)
            }
        }
    }

}

@Composable
fun Lib(Thing : L){
    Column() {
        Row(modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row {
                Icon(painter = painterResource(Thing.id),"",Modifier.padding(end = 15.dp))
                Text(Thing.title)
            }
            Icon(imageVector = Icons.Default.KeyboardArrowRight,
                "")
        }
        Divider(thickness = 5.dp)
    }
}