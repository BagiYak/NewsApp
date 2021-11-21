package com.example.news.feature_news.presentation.news

sealed class NewsEvent {
    data class GetNews(val newsEndpoint: NewsEndpoint): NewsEvent()
    object GetSavedNews: NewsEvent()
}