package com.example.news.feature_news.presentation.util

sealed class Screen(val route: String) {
    object NewsScreen: Screen("news_screen")
    object BreakingNewsScreen: Screen("breaking_news_screen")
    object SavedNewsScreen: Screen("saved_news_screen")
    object ArticleScreen: Screen("article_screen")
}
