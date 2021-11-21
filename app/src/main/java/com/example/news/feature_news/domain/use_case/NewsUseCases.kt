package com.example.news.feature_news.domain.use_case

data class NewsUseCases(
    val getNews: GetNews,
    val saveArticle: SaveArticle,
    val getArticles: GetArticles,
)
