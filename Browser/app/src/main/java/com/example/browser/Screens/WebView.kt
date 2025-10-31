package com.example.browser.Screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.browser.MainViewModel
import com.example.browser.R
import com.example.browser.Web
import com.mrtdk.glass.GlassBox
import com.mrtdk.glass.GlassContainer
import kotlinx.coroutines.launch
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WebView(NavController: NavController, ViewModel: MainViewModel) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var goBack: (() -> Unit)? by remember { mutableStateOf(null) }
    var showSheet by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current
    val switch = remember { mutableStateOf(true) }

    Scaffold {
        BackHandler(enabled = true) { goBack?.invoke() }

        GlassContainer(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            content = {
                Box(modifier = Modifier.padding(it)) {
                    Web(viewModel = ViewModel, onGoBackReady = { backFunc -> goBack = backFunc })
                }
            }
        ) {
            if (expanded) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { expanded = false }
                )
            }

            // Bottom search bar animation
            AnimatedVisibility(
                visible = ViewModel.scroll.value,
                enter = slideInVertically(
                    initialOffsetY = {it->
                        it },
                    animationSpec = tween(durationMillis = 400)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it->
                        it },
                    animationSpec = tween(durationMillis = 400)
                ),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                val width by animateDpAsState(
                    targetValue = if (expanded) 250.dp else 250.dp,
                    animationSpec = spring(dampingRatio = 0.4f)
                )

                val height by animateDpAsState(
                    targetValue = if (expanded) 100.dp else 50.dp,
                    animationSpec = spring(dampingRatio = 0.4f)
                )

                val blur = if (expanded) 0.8f else 0.3f

                GlassBox(
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .size(width = width, height = height)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    expanded = true
                                    if (switch.value) {
                                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                    }
                                },
                                onLongPress = {
                                    clipboardManager.setText(AnnotatedString(ViewModel.currentUrl.value))
                                    Toast.makeText(context, "URL Copied!", Toast.LENGTH_SHORT).show()
                                    if (switch.value) haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                }
                            )
                        },
                    warpEdges = 0.4f,
                    blur = blur,
                    scale = 0.3f,
                    shape = RoundedCornerShape(25.dp),
                    elevation = 10.dp,
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    ) {
                        if (expanded) {
                            BasicTextField(
                                value = ViewModel.url.value,
                                onValueChange = { ViewModel.url.value = it },
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxSize(),
                                textStyle = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Gray
                                ),
                                singleLine = false,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(
                                    onSearch = { NavController.navigate(Screens.WEBVIEW) }
                                )
                            )
                        } else {
                            Text(
                                text = ViewModel.currentUrl.value,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .padding(12.dp)
                            )
                        }
                    }
                }
            }

            // Home button - slides from left
            AnimatedVisibility(
                visible = ViewModel.scroll.value,
                enter = slideInHorizontally(
                    initialOffsetX = { -it},
                    animationSpec = tween(durationMillis = 400)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { -it},
                    animationSpec = tween(durationMillis = 400)
                ),
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                GlassBox(
                    modifier = Modifier
                        .padding(start = 20.dp, bottom = 30.dp)
                        .size(50.dp),
                    warpEdges = 0.5f,
                    blur = 0.3f,
                    scale = 0.4f,
                    shape = CircleShape,
                    elevation = 20.dp,
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            if (ViewModel.currentUrl.value.contains("google") ||
                                ViewModel.currentUrl.value.contains("duck")
                            ) {
                                NavController.navigate(Screens.HOMEPAGE)
                                NavController.popBackStack(Screens.WEBVIEW, inclusive = true)
                                if (switch.value) haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            } else {
                                goBack?.invoke()
                                if (switch.value) haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            }
                        },
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        ),
                        shape = CircleShape
                    ) {}

                    if ((ViewModel.currentUrl.value.contains("google") ||
                                ViewModel.currentUrl.value.contains("duck")) && !expanded
                    ) {
                        Icon(Icons.Default.Home, "Home", tint = Color.Gray)
                    } else if (expanded) {
                        Image(painter = painterResource(ViewModel.search.value), contentDescription = "")
                    } else {
                        Icon(Icons.Default.ArrowBack, "Back", tint = Color.Gray)
                    }
                }
            }

            // Options button - slides from right
            AnimatedVisibility(
                visible = ViewModel.scroll.value,
                enter = slideInHorizontally(
                    initialOffsetX = { it},
                    animationSpec = tween(durationMillis = 400)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(durationMillis = 400)
                ),
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                GlassBox(
                    modifier = Modifier
                        .padding(end = 20.dp, bottom = 30.dp)
                        .size(50.dp),
                    warpEdges = 0.5f,
                    blur = 0.3f,
                    scale = 0.4f,
                    shape = CircleShape,
                    elevation = 20.dp,
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            if (expanded) {
                                NavController.navigate(Screens.WEBVIEW)
                                if (switch.value) haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            } else {
                                showSheet = true
                                if (switch.value) haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            }
                        },
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        ),
                        shape = CircleShape
                    ) {}

                    if (expanded) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = "",
                            tint = Color.Gray
                        )
                    } else {
                        Icon(Icons.Default.MoreVert, contentDescription = "", tint = Color.Gray)
                    }
                }
            }
        }
    }

    // Modal bottom sheet
    val options = listOf("Google", "DuckDuckGo")
    var selectedOption by remember { mutableStateOf(options[0]) }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Divider(
                        modifier = Modifier
                            .width(40.dp)
                            .height(4.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column {
                    var expandedMenu by remember { mutableStateOf(false) }
                    val optionsList = listOf(R.drawable.google, R.drawable.duck)
                    Box(contentAlignment = Alignment.Center) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                "Default Search Engine: ",
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp
                            )
                            Image(
                                painter = painterResource(ViewModel.search.value),
                                contentDescription = "",
                                modifier = Modifier.clickable {
                                    expandedMenu = true
                                    if (switch.value){
                                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                    }

                                }
                            )
                        }

                        DropdownMenu(
                            expanded = expandedMenu,
                            onDismissRequest = { expandedMenu = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            options.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        selectedOption = option
                                        ViewModel.searchengine.value = option
                                        when (selectedOption) {
                                            "Google" -> ViewModel.search.value = R.drawable.google
                                            "DuckDuckGo" -> ViewModel.search.value = R.drawable.duck
                                        }
                                        expandedMenu = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Haptics:                         ",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                        Switch(
                            checked = switch.value,
                            onCheckedChange = {
                                switch.value = it
                                haptic.performHapticFeedback(HapticFeedbackType.ToggleOn)
                            }
                        )
                    }
                }
            }
        }
    }
}
