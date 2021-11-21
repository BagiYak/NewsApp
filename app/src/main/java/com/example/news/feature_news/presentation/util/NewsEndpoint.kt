package com.example.news.feature_news.presentation.util

sealed class NewsEndpoint {
    object EverythingNews: NewsEndpoint()
    object BreakingNews: NewsEndpoint()
}
