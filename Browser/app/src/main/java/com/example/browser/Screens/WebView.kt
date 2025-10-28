package com.example.browser.Screens

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Divider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
Piyus
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WebView(NavController : NavController,ViewModel : MainViewModel) {

    var goBack: (() -> Unit)? by remember { mutableStateOf(null) }
    var showSheet by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }


    Scaffold(

    ) {

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
            // If expanded, show transparent overlay (acts like onDismissRequest)
            if (expanded) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { expanded = false } // 👈 collapse on outside click
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

            // Your expandable GlassBox
            GlassBox(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 30.dp)
                    .size(width = width, height = height)
                    .clickable { expanded = true },
                warpEdges = 0.4f,
                blur = 0.3f,
                scale = 0.3f,
                shape = RoundedCornerShape(25.dp),
                elevation = 10.dp,
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.fillMaxSize().align(Alignment.Center)) {
                    if (expanded) {
                            BasicTextField(
                                value = ViewModel.url.value,
                                onValueChange = {
                                    ViewModel.url.value = it
                                },
                                modifier = Modifier.padding(12.dp).align(Alignment.CenterEnd),
                                textStyle = TextStyle(fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily.SansSerif)
                            )

                    } else {
                        Text(
                            text = ViewModel.currentUrl.value,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 5.dp).padding(12.dp)
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
                        if (ViewModel.currentUrl.value.contains("google")) {
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
                if (ViewModel.currentUrl.value.contains("google") && !expanded) {
                    Icon(
                        Icons.Default.Home,
                        "Home"
                    )

                }
                else if(expanded){
                    Image(painter = painterResource(R.drawable.img),"")
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


    // Bottom Sheet
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            ),
            dragHandle = {  // Optional handle at the top
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
                Text(
                    text = "This is a Modal Bottom Sheet!",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "It slides up smoothly, has rounded corners, and can be dismissed by tapping outside or swiping down."
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = { showSheet = false }) {
                    Text("Close Sheet")
                }
            }
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Web(
    viewModel: MainViewModel,
    onGoBackReady: (()->Unit) -> Unit // 👈 send function reference to parent
) {
    var currentUrl by remember { mutableStateOf("") }
    val webView = remember { mutableStateOf<WebView?>(null) }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
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

                loadUrl("https://www.google.com/search?q=${viewModel.url.value}")
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

    // optional system back handler
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

