package com.example.browser.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.browser.MainViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.rememberDismissState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.Icon

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun History(viewModel: MainViewModel, NavController: NavHostController) {
    val historyList by viewModel.allHistory.observeAsState(emptyList())
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold {
        Box(modifier = Modifier.padding(it).padding(horizontal = 10.dp)) {
            Column {
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "History",
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.height(15.dp))

                LazyColumn {
                    items(historyList.asReversed(), key = { item -> item.id }) { item ->
                        val dismissState = rememberDismissState(
                            confirmStateChange = { value ->
                                if (value == DismissValue.DismissedToStart) {
                                    scope.launch {
                                        viewModel.deleteHistory(item)
                                        Toast.makeText(context, "Deleted: ${item.title}", Toast.LENGTH_SHORT).show()
                                    }
                                    true
                                } else false
                            }
                        )

                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(DismissDirection.EndToStart),
                            background = {
                                val direction = dismissState.dismissDirection
                                if (direction == DismissDirection.EndToStart) {
                                    // Red background only revealed area
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(vertical = 5.dp)
                                            .border(
                                                width = 1.dp,
                                                color = Color.Transparent,
                                                shape = RoundedCornerShape(25)
                                            ),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .fillMaxWidth(
                                                    fraction =  // show only revealed space
                                                        when {
                                                            dismissState.progress.fraction < 0f -> 0f
                                                            dismissState.progress.fraction > 1f -> 1f
                                                            else -> dismissState.progress.fraction
                                                        }
                                                )
                                                .background(Color.Red, RoundedCornerShape(25.dp))
                                        )
                                        Row(
                                            modifier = Modifier
                                                .padding(end = 25.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint = Color.White
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("Delete", color = Color.White, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            },
                            dismissContent = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(25)
                                        )
                                        .pointerInput(Unit) {
                                            detectTapGestures(
                                                onTap = {
                                                    Toast.makeText(context, "Clicked: ${item.title}", Toast.LENGTH_SHORT).show()
                                                    viewModel.url.value = item.url
                                                    NavController.navigate(Screens.WEBVIEW)
                                                    NavController.popBackStack(Screens.HISTORY, inclusive = true)
                                                },
                                                onLongPress = {
                                                    clipboardManager.setText(AnnotatedString(item.url))
                                                    Toast.makeText(context, "URL Copied!", Toast.LENGTH_SHORT).show()
                                                }
                                            )
                                        }
                                        .padding(horizontal = 10.dp, vertical = 5.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            item.title + " : ",
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Text(item.url)
                                        Spacer(modifier = Modifier.width(10.dp))
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }
    }
}
