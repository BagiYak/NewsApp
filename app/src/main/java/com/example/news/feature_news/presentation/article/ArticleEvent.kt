package com.example.news.feature_news.presentation.article

sealed class ArticleEvent {
    object SaveArticle: ArticleEvent()
    object DeleteArticle: ArticleEvent()
}