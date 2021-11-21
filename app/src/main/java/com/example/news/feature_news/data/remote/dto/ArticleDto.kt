package com.example.news.feature_news.data.remote.dto

import com.example.news.feature_news.domain.model.Article
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArticleDto(

    @SerializedName("author")
    @Expose
    val author: String?,

    @SerializedName("title")
    @Expose
    val title: String?,

    @SerializedName("description")
    @Expose
    val description: String?,

    @SerializedName("url")
    @Expose
    val url: String?,

    @SerializedName("urlToImage")
    @Expose
    val urlToImage: String?,

    @SerializedName("publishedAt")
    @Expose
    val publishedAt: String?,

    @SerializedName("content")
    @Expose
    val content: String?,
) {
    fun toArticle(): Article {
        return Article(
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