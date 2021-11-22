package com.example.news.feature_news.domain.use_case

data class NewsUseCases(
    val getNews: GetNews,
    val saveArticle: SaveArticle,
    val deleteArticle: DeleteArticle,
    val getArticles: GetArticles,
)
