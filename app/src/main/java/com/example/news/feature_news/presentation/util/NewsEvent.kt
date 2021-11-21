package com.example.news.feature_news.presentation.util

import com.example.news.feature_news.domain.model.Article

sealed class NewsEvent {
    data class SaveArticle(val article: Article): NewsEvent()
    data class GetNews(val newsEndpoint: NewsEndpoint): NewsEvent()
}