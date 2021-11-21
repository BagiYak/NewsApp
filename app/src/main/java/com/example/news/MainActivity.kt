package com.example.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.news.feature_news.domain.model.BottomNavItem
import com.example.news.feature_news.presentation.BreakingNewsViewModel
import com.example.news.feature_news.presentation.NewsViewModel
import com.example.news.feature_news.presentation.composable.BottomNavigationBar
import com.example.news.feature_news.presentation.composable.Navigation
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
                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()

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
                }

                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "All news",
                                    route = "news",
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_all_news),
                                    badgeCount = newsViewModel.state.value.newsItems.size
                                ),
                                BottomNavItem(
                                    name = "Top news",
                                    route = "breaking_news",
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_breaking_news),
                                    badgeCount = breakingNewsViewModel.state.value.newsItems.size
                                ),
                                BottomNavItem(
                                    name = "Saved news",
                                    route = "saved_news",
                                    icon = ImageVector.vectorResource(id = R.drawable.ic_bookmark),
                                    badgeCount = 0
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