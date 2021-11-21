package com.example.news.feature_news.presentation.util

import com.example.news.feature_news.domain.model.Article

data class NewsState(
    val newsItems: List<Article> = emptyList(),
    val isLoading: Boolean = false,
)
