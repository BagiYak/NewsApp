package com.example.news.feature_news.presentation.util

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.news.feature_news.domain.model.Article
import com.example.news.feature_news.presentation.article.ArticleViewModel
import com.example.news.feature_news.presentation.article.components.ArticleScreen
import com.example.news.feature_news.presentation.news.BreakingNewsViewModel
import com.example.news.feature_news.presentation.news.NewsViewModel
import com.example.news.feature_news.presentation.news.SavedNewsViewModel
import com.example.news.feature_news.presentation.news.components.BreakingNewsScreen
import com.example.news.feature_news.presentation.news.components.NewsScreen
import com.example.news.feature_news.presentation.news.components.SavedNewsScreen
import com.google.gson.Gson

@Composable
fun Navigation(navController: NavHostController) {

    val newsViewModel: NewsViewModel = viewModel()
    val breakingNewsViewModel: BreakingNewsViewModel = viewModel()
    val savedNewsViewModel: SavedNewsViewModel = viewModel()
    val articleViewModel: ArticleViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.BreakingNewsScreen.route) {
        composable(route = Screen.BreakingNewsScreen.route) {
            BreakingNewsScreen(navController, breakingNewsViewModel)
        }
        composable(route = Screen.NewsScreen.route) {
            NewsScreen(navController, newsViewModel)
        }
        composable(route = Screen.SavedNewsScreen.route) {
            SavedNewsScreen(navController, savedNewsViewModel)
        }
        composable(
            route = Screen.ArticleScreen.route +
                    "?article={article}",
            arguments = listOf(
                navArgument(
                    name = "article"
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            it.arguments?.getString("article")?.let { jsonArticle ->
                ArticleScreen(
                    navController = navController,
                    viewModel = articleViewModel,
                    savedNewsViewModel = savedNewsViewModel,
                    article = Gson().fromJson(jsonArticle, Article::class.java)
                )
            }
        }
    }
}