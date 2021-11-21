package com.example.news.feature_news.presentation.composable

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
import com.example.news.feature_news.presentation.util.NewsEndpoint
import com.example.news.feature_news.presentation.util.NewsEvent
import com.example.news.feature_news.presentation.NewsItem
import com.example.news.feature_news.presentation.NewsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NewsScreen(viewModel: NewsViewModel) {

    val state = viewModel.state.value
    val isRefreshing = rememberSwipeRefreshState(false)

    SwipeRefresh(
        state = isRefreshing,
        onRefresh = {
            isRefreshing.isRefreshing = true
            viewModel.onEvent(NewsEvent.GetNews(NewsEndpoint.EverythingNews))
            isRefreshing.isRefreshing = false
        },
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "All news",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.newsItems.size) { i ->
                        Text(
                            text = "Article $i",
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Right
                        )
                        val article = state.newsItems[i]
                        if(i > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        NewsItem(article = article)
                        if(i < state.newsItems.size - 1) {
                            Divider()
                        }
                    }
                }
            }
            if(state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}