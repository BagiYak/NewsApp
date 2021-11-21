package com.example.news.feature_news.presentation.composable

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.news.feature_news.presentation.BreakingNewsViewModel
import com.example.news.feature_news.presentation.NewsViewModel

@Composable
fun Navigation(navController: NavHostController) {

    val newsViewModel: NewsViewModel = viewModel()
    val breakingNewsViewModel: BreakingNewsViewModel = viewModel()

    NavHost(navController = navController, startDestination = "breaking_news") {
        composable("breaking_news") {
            BreakingNewsScreen(breakingNewsViewModel)
        }
        composable("news") {
            NewsScreen(newsViewModel)
        }
        composable("saved_news") {
            SavedNewsScreen()
        }
    }
}