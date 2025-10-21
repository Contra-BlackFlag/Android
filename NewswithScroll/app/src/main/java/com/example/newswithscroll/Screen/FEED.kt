package com.example.newswithscroll.Screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.example.newswithscroll.Viewmodel.MainViewModel
import kotlin.math.absoluteValue
import kotlin.random.Random
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FEED(viewModel: MainViewModel, innerpadding: PaddingValues){

    val newsList = viewModel.NewsDataState.value.list
    val isListEmpty = newsList.isEmpty()
    val pageCount = if (isListEmpty) 0 else newsList.size

    // --- CRITICAL FIX START ---
    // 1. Calculate a valid initial page index.
    // 2. If the list is empty (size 0), the initial page must be 0.
    // 3. Otherwise, pick a random number between 0 (inclusive) and size (exclusive).
    val initialPageIndex = remember(pageCount) {
        if (pageCount > 0) Random.nextInt(0, pageCount) else 0
    }

    val pagerState = rememberPagerState(
        initialPage = initialPageIndex,
        pageCount = { pageCount }
    )
    // --- CRITICAL FIX END ---

    // Show a loading indicator if the list is empty and we are still loading
    if (viewModel.NewsDataState.value.loading && isListEmpty) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.padding(innerpadding))
        }
        return
    }

    // Show an error message if there's an error
    if (viewModel.NewsDataState.value.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Error loading feed: ${viewModel.NewsDataState.value.error}",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
        return
    }

    // Only render the Pager if there are pages to show (pageCount > 0)
    if (pageCount == 0) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No news found.", modifier = Modifier.padding(16.dp))
        }
        return
    }

    VerticalPager(state = pagerState) { page ->
        val article = newsList[page] // Get the current article

        Card(
            Modifier
                .fillMaxSize().padding(innerpadding)
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Ensure URL is not null before attempting to load image
                if (article.urlToImage != null) {
                    Card (shape = CardDefaults.elevatedShape,
                        modifier = Modifier
                            .padding(8.dp) // Image takes up max 50% of screen height
                    ){
                        AsyncImage(
                            model = article.urlToImage,
                            contentDescription = article.title,
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))

                // Scrollable text content for the bottom half
                Box( modifier = Modifier.padding(10.dp)){
                    Column (){
                        Text(article.title + " : ",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            lineHeight = 33.sp
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(article.content,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.padding(10.dp))

                        // Ensure URL exists before showing link
                        if (article.url != null) {
                            ClickableLinkExample(article.url)
                        }

                        Text(": "+article.author,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ClickableLinkExample(url : String) {
    val uriHandler = LocalUriHandler.current

    Text(
        buildAnnotatedString {
            append("Visit the ")
            withLink(
                LinkAnnotation.Url(
                    url = url,
                    styles = TextLinkStyles(style = SpanStyle(color = Color.Blue)) // Optional: Customize link appearance
                )
            ) {
                append("Website")
            }
            append(" for reading more")
        }
    )
}