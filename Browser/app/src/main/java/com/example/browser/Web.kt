package com.example.browser

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
