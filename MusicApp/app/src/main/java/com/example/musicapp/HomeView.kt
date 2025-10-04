package com.example.musicapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicapp.BrowserItem
import kotlinx.coroutines.flow.combine

@Composable
fun HomeView(){
    val categories = listOf(
        "Hits","Happy","Workout","Running","TGIF","Yoga"
    )
    val grouped = listOf<String>("New Release", "Favourite", "Top Rated").groupBy {
        it[0]
    }

    LazyColumn {
        grouped.forEach {
            stickyHeader {
                Text(text = "Music", modifier = Modifier.padding(16.dp))
                LazyRow {
                    items(categories) {

                        BrowserItem(categories = it, drawable = R.drawable.outline_album_24)

                    }
                }
            }
        }
    }

}
@Composable
fun BrowserItem(categories : String,drawable : Int){
    Card(modifier = Modifier.padding(16.dp).size(200.dp),
        border = BorderStroke(7.dp, color = Color.DarkGray)) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(categories)
            Image(painter = painterResource(drawable),
                contentDescription = categories
            )
        }
    }
}