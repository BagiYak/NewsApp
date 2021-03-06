package com.example.news.feature_news.presentation.news.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.news.feature_news.presentation.news.SavedNewsViewModel

@Composable
fun SavedNewsScreen(
    navController: NavHostController,
    viewModel: SavedNewsViewModel
) {

    val state = viewModel.state

    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.value.newsItems.value.size) { i ->
                    Text(
                        text = "Article ${i + 1}",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )
                    val article = state.value.newsItems.value[i]
                    if(i > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    NewsItem(
                        navController = navController,
                        article = article
                    )
                    if(i < state.value.newsItems.value.size - 1) {
                        Divider()
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
        if(state.value.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}