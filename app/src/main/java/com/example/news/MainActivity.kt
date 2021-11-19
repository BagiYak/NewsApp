package com.example.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.news.feature_news.presentation.NewsItem
import com.example.news.feature_news.presentation.NewsViewModel
import com.example.news.ui.theme.NewsTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            NewsTheme {

                val viewModel: NewsViewModel = hiltViewModel()
                val state = viewModel.state.value
                val scaffoldState = rememberScaffoldState()
                val isRefreshing = rememberSwipeRefreshState(false)

                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest { event ->
                        when(event) {
                            is NewsViewModel.UIEvent.ShowSnackbar -> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                        }
                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    SwipeRefresh(
                        state = isRefreshing,
                        onRefresh = {
                            isRefreshing.isRefreshing = true
                            viewModel.onGetBreakingNews()
                            lifecycleScope.launch {
                                viewModel.getBreakingNewsJob?.join()
                                isRefreshing.isRefreshing = false
                            }
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
                                    text = "Breaking news",
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center
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

            }

        }

    }
}