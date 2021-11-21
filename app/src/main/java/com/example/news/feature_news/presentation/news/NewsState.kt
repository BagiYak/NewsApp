package com.example.news.feature_news.presentation.news

import com.example.news.feature_news.domain.model.Article

data class NewsState(
    val newsItems: List<Article> = emptyList(),
    val isLoading: Boolean = false,
)
