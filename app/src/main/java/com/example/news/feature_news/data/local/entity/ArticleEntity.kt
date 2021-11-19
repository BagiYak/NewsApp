package com.example.news.feature_news.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.news.feature_news.domain.model.Article

@Entity(
    tableName = "articles"
)
data class ArticleEntity(
    @PrimaryKey
    var id: Int? = null,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    val favorite: Boolean = false,
) {
    fun toArticle(): Article {
        return Article(
            id = id,
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
