package com.example.news.feature_news.presentation.article.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.news.feature_news.domain.model.Article
import com.example.news.feature_news.presentation.article.ArticleState
import com.example.news.feature_news.presentation.article.ArticleViewModel
import com.example.news.feature_news.presentation.news.components.NewsItem

@Composable
fun ArticleScreen(
    navController: NavHostController,
    viewModel: ArticleViewModel,
    article: Article,
) {
    viewModel.state.value = ArticleState(article)
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
    ) {
        state.article?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                NewsItem(
                    navController = navController,
                    article = state.article)
            }
        }
    }
}