package com.example.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.news.feature_news.domain.model.BottomNavItem
import com.example.news.feature_news.presentation.article.ArticleViewModel
import com.example.news.feature_news.presentation.news.BreakingNewsViewModel
import com.example.news.feature_news.presentation.news.NewsViewModel
import com.example.news.feature_news.presentation.news.SavedNewsViewModel
import com.example.news.feature_news.presentation.news.components.BottomNavigationBar
import com.example.news.feature_news.presentation.util.Navigation
import com.example.news.feature_news.presentation.util.Screen
import com.example.news.ui.theme.BottomNavWithBadgesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            BottomNavWithBadgesTheme {

                val newsViewModel: NewsViewModel = viewModel()
                val breakingNewsViewModel: BreakingNewsViewModel = viewModel()
                val savedNewsViewModel: SavedNewsViewModel = viewModel()
                val articleViewModel: ArticleViewModel = viewModel()
                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                LaunchedEffect(key1 = true) {
                    newsViewModel.eventFlow.collectLatest { event ->
                        when(event) {
                            is NewsViewModel.UIEvent.ShowSnackbar -> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                        }
                    }
                    articleViewModel.eventFlow.collectLatest { event ->
                        when(event) {
                            is ArticleViewModel.UIEvent.ShowSnackbar -> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                        }
                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            backgroundColor = Color.Green,
                            title = {
                                when(navBackStackEntry?.destination?.route) {
                                    Screen.NewsScreen.route -> {
                                        Text("News")
                                    }
                                    Screen.BreakingNewsScreen.route -> {
                                        Text("Breaking News")
                                    }
                                    Screen.SavedNewsScreen.route -> {
                                        Text("Saved News")
                                    }
                                    Screen.ArticleScreen.route +
                                            "?article={article}" -> {
                                        Text("Article")
                                    }
                                }
                            },
                            navigationIcon = {
                                if(navBackStackEntry?.destination?.route == Screen.ArticleScreen.route +
                                    "?article={article}") {
                                    IconButton(
                                        // navigate to the back stack
                                        onClick = { navController.navigateUp() }
                                    ) {
                                        Icon(painter = painterResource(id = (R.drawable.ic_arrow_back)), contentDescription = "arrow back icon")
                                    }
                                }
                            },
                        )
                    },
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "All news",
                                    route = Screen.NewsScreen.route,
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_all_news),
                                    badgeCount = newsViewModel.state.value.newsItems.value.size
                                ),
                                BottomNavItem(
                                    name = "Top news",
                                    route = Screen.BreakingNewsScreen.route,
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_breaking_news),
                                    badgeCount = breakingNewsViewModel.state.value.newsItems.value.size
                                ),
                                BottomNavItem(
                                    name = "Saved news",
                                    route = Screen.SavedNewsScreen.route,
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_bookmark),
                                    badgeCount = savedNewsViewModel.state.value.newsItems.value.size
                                )
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {
                    Navigation(
                        navController = navController
                    )
                }

            }

        }

    }
}