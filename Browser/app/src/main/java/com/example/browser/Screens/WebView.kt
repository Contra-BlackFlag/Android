package com.example.browser.Screens

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.browser.MainViewModel
import com.mrtdk.glass.GlassBox
import com.mrtdk.glass.GlassContainer
import com.example.browser.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WebView(NavController : NavController,ViewModel : MainViewModel) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var goBack: (() -> Unit)? by remember { mutableStateOf(null) }
    var showSheet by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }


    Scaffold(

    ) {
            BackHandler (enabled = true){
                goBack?.invoke()
            }

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

            val width by animateDpAsState(
                targetValue = if (expanded) 250.dp else 250.dp,
                animationSpec = spring(dampingRatio = 0.4f)
            )

            val height by animateDpAsState(
                targetValue = if (expanded) 100.dp else 50.dp,
                animationSpec = spring(dampingRatio = 0.4f)
            )
            val bottomPadding = if (expanded) 130.dp else 30.dp

            GlassBox(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 30.dp)
                    .size(width = width, height = height)
                    .clickable { expanded = true }
                    .pointerInput(Unit){
                        detectTapGestures(
                            onLongPress = {
                                clipboardManager.setText(AnnotatedString(ViewModel.currentUrl.value))
                                Toast.makeText(context, "URL Copied!", Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                warpEdges = 0.4f,
                blur = 0.3f,
                scale = 0.3f,
                shape = RoundedCornerShape(25.dp),
                elevation = 10.dp,
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)) {
                    if (expanded) {
                            BasicTextField(
                                value = ViewModel.url.value,
                                onValueChange = {
                                    ViewModel.url.value = it
                                },
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxSize(),
                                textStyle = TextStyle(fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily.SansSerif),
                                singleLine = false,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        NavController.navigate(Screens.WEBVIEW)
                                    }
                                )
                            )

                    } else {
                        Text(
                            text = ViewModel.currentUrl.value,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .padding(12.dp)
                        )
                    }
                }
            }
            GlassBox(
                modifier = Modifier
                    .align(Alignment.BottomStart)
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
                        if (ViewModel.currentUrl.value.contains("google")
                            ||
                            ViewModel.currentUrl.value.contains("duck") ) {
                            NavController.navigate(Screens.HOMEPAGE)
                            NavController.popBackStack(Screens.WEBVIEW, inclusive = true)

                        } else {
                            goBack?.invoke()
                        }
                    },
                    colors = ButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    ),
                    shape = CircleShape
                ) { }
                if ((ViewModel.currentUrl.value.contains("google")
                            ||
                            ViewModel.currentUrl.value.contains("duck"))
                    && !expanded) {
                    Icon(
                        Icons.Default.Home,
                        "Home"
                    )

                }
                else if(expanded){
                    Image(painter = painterResource(ViewModel.search.value),"")
                }
                else {
                    Icon(
                        Icons.Default.ArrowBack,
                        "back"
                    )
                }
            }
            GlassBox(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
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
                        if (expanded){
                                NavController.navigate(Screens.WEBVIEW)
                        }
                        else{
                            showSheet = true
                        }

                    },
                    colors = ButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    ),
                    shape = CircleShape
                ) {

                }
                if (expanded){
                    Icon(painter = painterResource(R.drawable.search), "")
                }
                else{
                    Icon(Icons.Default.MoreVert, "",)
                }



            }


        }



    }
    val options = listOf("Google", "DuckDuckGo")
    var selectedOption by remember { mutableStateOf(options[0]) }


    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            ),
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


                   var expanded by remember { mutableStateOf(false) }
                   val items = listOf(R.drawable.google,R.drawable.duck)
                   Box(contentAlignment = Alignment.Center) {
                       Box(
                           modifier = Modifier
                               .fillMaxWidth()
                               .size(width = 200.dp, height = 50.dp)
                       ) {

                           Row(
                               horizontalArrangement = Arrangement.SpaceBetween
                           ) {
                               Text("Default Search Engine:       ", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                               Image(painter = painterResource(ViewModel.search.value)
                                   ,"",
                                   modifier = Modifier
                                       .clickable { expanded = true })
                               Spacer(modifier = Modifier.padding(start = 5.dp, end = 2.dp))
                           }

                       }

                       DropdownMenu(
                           expanded = expanded,
                           onDismissRequest = { expanded = false },
                           modifier = Modifier.background(Color.White)
                       ) {
                           options.forEach { option ->
                               DropdownMenuItem(
                                   text = { Text(option) },
                                   onClick = {
                                       selectedOption = option
                                       ViewModel.searchengine.value = option
                                       when(selectedOption){
                                           "Google"-> ViewModel.search.value = R.drawable.google
                                           "DuckDuckGo"->ViewModel.search.value = R.drawable.duck
                                       }
                                       expanded = false
                                   }
                               )
                           }
                       }
                   }
               }





               }
            }
        }
    }



@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Web(
    viewModel: MainViewModel,
    onGoBackReady: (()->Unit) -> Unit 
) {
    var currentUrl by remember { mutableStateOf("") }
    val webView = remember { mutableStateOf<WebView?>(null) }

    AndroidView(
        factory = {
            WebView(it).apply {
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(false)

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        url?.let {
                            currentUrl = it
                            viewModel.currentUrl(it)
                        }
                    }
                }

                loadUrl("https://www.${viewModel.searchengine.value}.com/search?q=${viewModel.url.value}")
                webView.value = this
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { webView.value = it }
    )

    LaunchedEffect(webView.value) {
        webView.value?.let { web ->
            onGoBackReady {
                if (web.canGoBack()) web.goBack()
            }
        }
    }


    BackHandler(enabled = webView.value?.canGoBack() == true) {
        webView.value?.goBack()
    }

    GlobalScope.launch (Dispatchers.IO){
        delay(5000)
        viewModel.url.value = ""
    }

}

@Composable
fun ExpandableCircle() {
    var expanded by remember { mutableStateOf(false) }
    val size by animateDpAsState(
        targetValue = if (expanded) 150.dp else 70.dp,
        animationSpec = spring(dampingRatio = 0.4f)
    )

    Box(
        modifier = Modifier
            .size(size)
            .background(Color.Red, shape = CircleShape)
            .clickable { expanded = !expanded },
        contentAlignment = Alignment.Center
    ) {
        Text("Tap", color = Color.White)
    }
}

