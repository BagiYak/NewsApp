package com.example.news.feature_news.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @SerializedName("articles")
    @Expose
    val articles: MutableList<ArticleDto>,

    @SerializedName("status")
    @Expose
    val status: String,

    @SerializedName("totalResults")
    @Expose
    val totalResults: Int,

)
