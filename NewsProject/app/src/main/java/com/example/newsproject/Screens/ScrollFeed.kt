package com.example.newsproject.Screens

import android.annotation.SuppressLint
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
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
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
import com.example.newsproject.ViewModel.MainViewModel
import kotlin.math.absoluteValue
import kotlin.random.Random
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsproject.Navigation.Screens
import com.example.newsproject.R
@SuppressLint( "ViewModelConstructorInComposable")
@Composable
fun Navigation() {
    val context = LocalContext.current
    val viewmodel = MainViewModel()
    val NavController = rememberNavController()
    val currentBackStack by NavController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route
    val scrollfeed = remember { mutableStateOf(false) }
    val feed = remember { mutableStateOf(false) }

    val bottomBarRoutes = listOf(Screens.FEED, Screens.SCROLL_FEED)

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Screens.FEED,
                        onClick = { NavController.navigate(Screens.FEED) {
                            popUpTo(NavController.graph.startDestinationId)
                            launchSingleTop = true
                        } },
                        label = { Text("News") },
                        icon = { Icon(painter = painterResource(id = R.drawable.home), contentDescription = "Home") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Screens.SCROLL_FEED,
                        onClick = { NavController.navigate(Screens.SCROLL_FEED) {
                            popUpTo(NavController.graph.startDestinationId)
                            launchSingleTop = true
                        } },
                        label = { Text("Scroll") },
                        icon = { Icon(painter = painterResource(id = R.drawable.scroll), contentDescription = "Scroll") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = NavController,
            startDestination = Screens.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.HOME) {
                Home(navController = NavController) // Bottom bar hidden here
            }
            composable(Screens.FEED) {
                Feed(viewmodel,NavController) // Bottom bar visible
            }
            composable(Screens.SCROLL_FEED) {
                ScrollFeed(viewmodel, innerPadding) // Bottom bar visible
            }
            composable(Screens.NewsFeedFullScreen + "/{url}/{title}/{content}/{newsurl}", arguments = listOf(
                navArgument("url"){ type = NavType.StringType },
                navArgument("title"){ type = NavType.StringType },
                navArgument("content"){ type = NavType.StringType },
                navArgument("newsurl"){type = NavType.StringType }
            )){ backStackEntry ->
                val url = backStackEntry.arguments?.getString("url") ?: ""
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val content = backStackEntry.arguments?.getString("content") ?: ""
                val newsurl = backStackEntry.arguments?.getString("newsurl")
                NewsFeedInFullScreen(url,title,content,newsurl)
            }
        }
    }
}



@Composable
fun ScrollFeed(viewModel: MainViewModel,paddingValues: PaddingValues) {
    val newsList = viewModel.NewsDataState.value.list
    val isListEmpty = newsList.isEmpty()
    val pageCount = if (isListEmpty) 0 else newsList.size

    val initialPageIndex = remember(pageCount) {
        if (pageCount > 0) Random.nextInt(0, pageCount) else 0
    }
    val pagerState = rememberPagerState(
        initialPage = initialPageIndex,
        pageCount = { pageCount }
    )
    Box(modifier = Modifier.padding()) {
        if (viewModel.NewsDataState.value.loading){
            CircularProgressIndicator()
        }
        VerticalPager(pagerState) {
            val article = newsList[it]
            Card(
                Modifier
                    .fillMaxSize()
                    .padding()
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - it) + pagerState
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
                    Card(
                        shape = CardDefaults.elevatedShape,
                        modifier = Modifier
                            .padding(8.dp) // Image takes up max 50% of screen height
                    ) {
                        AsyncImage(
                            model = article.urlToImage,
                            contentDescription = article.title,
                        )
                    }

                    Spacer(modifier = Modifier.padding(5.dp))

                    // Scrollable text content for the bottom half
                    Box(modifier = Modifier.padding(10.dp)) {
                        Column() {
                            Text(
                                article.title + " : ",
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                lineHeight = 33.sp
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                article.content,
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.padding(10.dp))

                            // Ensure URL exists before showing link
                            if (article.url != null) {
                                ClickableLinkExample(article.url)
                            }

                            Text(
                                ": " + article.author,
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
}
@Composable
fun ClickableLinkExample(url: String?) {
        val uriHandler = LocalUriHandler.current

        Text(
            buildAnnotatedString {
                append("Visit the ")
                url?.let {
                    withLink(
                        LinkAnnotation.Url(
                            url = it,
                            styles = TextLinkStyles(style = SpanStyle(color = Color.Blue)) // Optional: Customize link appearance
                        )
                    ) {
                        append("Website")
                    }
                }
                append(" for reading more")
            }
        )
    }