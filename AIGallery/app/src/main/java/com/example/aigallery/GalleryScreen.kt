package com.example.aigallery

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import java.io.File

@Composable
fun GalleryScreen(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = viewModel()
    val context = LocalContext.current

    // Observe both lists from the ViewModel
    val localImages by viewModel.localImages.observeAsState(initial = emptyList())
    val searchResults by viewModel.searchResults.observeAsState(initial = emptyList())
    val errorMessage by viewModel.error.observeAsState()

    // State for the search text field and to track if a search is active
    var query by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    // This runs once when the screen first appears to load local images
    LaunchedEffect(Unit) {
        viewModel.loadLocalImages(context.contentResolver)
    }

    // This is our main display logic
    val imagesToShow = remember(key1 = searchResults, key2 = localImages, key3 = isSearchActive) {
        if (isSearchActive) {
            // If a search is active, filter the local images
            val searchResultFileNames = searchResults.map {
                // Extract filename from the server URL
                Uri.parse(it.path).lastPathSegment
            }.toSet()

            localImages.filter { localUri ->
                // Check if the local image's filename is in the search results
                getFileNameFromUri(context.contentResolver, localUri) in searchResultFileNames
            }
        } else {
            // By default, show all local images
            localImages
        }
    }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search for images...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(onClick = {
                if (query.isNotBlank()) {
                    isSearchActive = true
                    viewModel.performSearch(query)
                }
            }) {
                Text("Search")
            }

            // Show a "Clear" button only when a search is active
            if (isSearchActive) {
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    isSearchActive = false
                    query = ""
                    viewModel.clearSearch()
                }) {
                    Text("Clear")
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let {
            Text(text = "Error: $it")
        }

        if (imagesToShow.isEmpty() && !isSearchActive) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No images found on device.")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(imagesToShow) { imageUri ->
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Gallery image",
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

// Helper function to get a filename from a content Uri
private fun getFileNameFromUri(resolver: android.content.ContentResolver, uri: Uri): String? {
    var fileName: String? = null
    resolver.query(uri, arrayOf(android.provider.MediaStore.Images.Media.DISPLAY_NAME), null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            fileName = cursor.getString(cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DISPLAY_NAME))
        }
    }
    return fileName
}