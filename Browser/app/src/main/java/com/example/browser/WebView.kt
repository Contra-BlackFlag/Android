package com.example.browser

import android.annotation.SuppressLint
import android.provider.SyncStateContract.Helpers.update
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.browser.Screens.Screens
import com.mrtdk.glass.GlassBox
import com.mrtdk.glass.GlassContainer


@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("SetJavaScriptEnabled", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WebView(NavController : NavController,ViewModel : MainViewModel) {

    var goBack: (() -> Unit)? by remember { mutableStateOf(null) }

    Scaffold(
    ) {

        GlassContainer(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            content = {
                Box(modifier = Modifier.padding(it)) {
                  Web(viewModel = ViewModel,
                      onGoBackReady = { backFunc -> goBack = backFunc })
                }
            }
        ) {
            GlassBox(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding( bottom = 30.dp)
                    .size(width = 250.dp, height = 50.dp),
                warpEdges = 0.4f,
                blur = 0.3f,
                scale = 0.3f,
                shape = RoundedCornerShape(25.dp),
                elevation = 10.dp,
                contentAlignment = Alignment.Center
            ) {

            Box(
                modifier = Modifier.padding().fillMaxSize()
            ){
                Text(text = ViewModel.currentUrl.value,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 5.dp).padding(12.dp))
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
                        if (ViewModel.currentUrl.value.contains("google")){
                            NavController.navigate(Screens.HOMEPAGE)
                            NavController.popBackStack(Screens.WEBVIEW, inclusive = true)

                        }
                        else{
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
                if (ViewModel.currentUrl.value.contains("google")) {
                    Icon(
                        Icons.Default.Home,
                        "Home"
                    )
                }
                else{
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

                    },
                    colors = ButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    ),
                    shape = CircleShape
                ){
                    Icon(Icons.Default.MoreVert,"")
                }


            }


        }


    }
}


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

    // ✅ Once webView is ready, expose goBack function to parent
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
}






