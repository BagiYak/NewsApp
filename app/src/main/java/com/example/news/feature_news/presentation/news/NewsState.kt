package com.example.news.feature_news.presentation.news

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.news.feature_news.domain.model.Article

data class NewsState(
    val newsItems: MutableState<List<Article>> = mutableStateOf(ArrayList()),
    val isLoading: Boolean = false,
)
