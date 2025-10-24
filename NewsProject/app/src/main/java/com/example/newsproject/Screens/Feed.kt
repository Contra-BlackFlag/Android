package com.example.newsproject.Screens

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.newsproject.Navigation.Screens
import com.example.newsproject.ViewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import kotlin.math.absoluteValue
import kotlin.random.Random

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Feed(viewModel: MainViewModel,NavController : NavController) {
    val newsList = viewModel.NewsDataStateCricket.value.list
    val isListEmpty = newsList.isEmpty()
    val pageCount = if (isListEmpty) 0 else newsList.size

    val initialPageIndex = remember(pageCount) {
        if (pageCount > 0) Random.nextInt(0, pageCount) else 0
    }
    val pagerState = rememberPagerState(
        initialPage = initialPageIndex,
        pageCount = { pageCount }
    )
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(10.dp)){
        HorizontalPager(
            count = viewModel.NewsDataStateCricket.value.list.size
        ) {
            Card(
                modifier = Modifier.clickable(
                    onClick ={
                        val safeUrl = Uri.encode(viewModel.NewsDataStateCricket.value.list[it].urlToImage)
                        NavController.navigate(Screens.NewsFeedFullScreen+"/${safeUrl}/${viewModel.NewsDataStateCricket.value.list[it].title}/${viewModel.NewsDataStateCricket.value.list[it].author}/${viewModel.NewsDataStateCricket.value.list[it].content}")
                    }
                )
            ) {
               AsyncImage(model = viewModel.NewsDataStateCricket.value.list[it].urlToImage,"")
            }
        }
    }
}
