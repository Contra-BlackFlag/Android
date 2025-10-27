package com.example.newsproject.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun NewsFeedInFullScreen(url: String, title: String, content: String, urltonews: String?) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Ensure URL is not null before attempting to load image
        Card(
            shape = CardDefaults.elevatedShape,
            modifier = Modifier
                .padding(8.dp) // Image takes up max 50% of screen height
        ) {
            AsyncImage(
                model = url,
                contentDescription = title
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        // Scrollable text content for the bottom half
        Box(modifier = Modifier.padding(10.dp)) {
            Column() {
                Text(
                    title + " : ",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    lineHeight = 33.sp
                )
                Spacer(modifier = Modifier.padding(5.dp))
                if (content != null) {
                    Text(
                        content,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))

                // Ensure URL exists before showing link
                if (url != null) {
                    ClickableLinkExample(urltonews)
                }
            }
        }
    }
}