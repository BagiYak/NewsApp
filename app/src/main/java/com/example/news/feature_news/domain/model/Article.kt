package com.example.news.feature_news.domain.model

import com.example.news.feature_news.data.local.entity.ArticleEntity

data class Article(
    val id: Int?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
) {
    fun toArticleEntity(): ArticleEntity {
        return ArticleEntity(
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content,
        )
    }
}
