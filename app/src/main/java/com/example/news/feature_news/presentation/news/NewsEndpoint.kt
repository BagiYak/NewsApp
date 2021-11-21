package com.example.news.feature_news.presentation.news

sealed class NewsEndpoint {
    object EverythingNews: NewsEndpoint()
    object BreakingNews: NewsEndpoint()
}
