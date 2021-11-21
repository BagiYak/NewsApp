package com.example.news.feature_news.presentation.article

import com.example.news.feature_news.domain.model.Article

data class ArticleState(
    val article: Article? = null,
    val isLoading: Boolean = false,
)