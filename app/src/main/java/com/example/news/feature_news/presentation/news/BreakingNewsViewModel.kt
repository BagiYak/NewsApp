package com.example.news.feature_news.presentation.news

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.core.util.Resource
import com.example.news.feature_news.domain.use_case.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NewsState())
    val state: State<NewsState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var getNewsJob: Job? = null

    init {
        onGetNews(NewsEndpoint.BreakingNews)
    }

    fun onEvent(event: NewsEvent) {
        when (event) {
            is NewsEvent.GetNews -> {
                onGetNews(event.newsEndpoint)
            }
        }
    }

    private fun onGetNews(newsEndpoint: NewsEndpoint) {
        getNewsJob?.cancel()

        getNewsJob = viewModelScope.launch {
            newsUseCases.getNews(newsEndpoint)
                .onEach { result ->
                    val news = result.data ?: emptyList()
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                newsItems = mutableStateOf(news),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                newsItems = mutableStateOf(news),
                                isLoading = false
                            )
                            _eventFlow.emit(
                                UIEvent.ShowSnackbar(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }
                        is Resource.Loading -> {
                            _eventFlow.emit(
                                UIEvent.ShowSnackbar(
                                    "Breaking News loading..."
                                )
                            )
                            _state.value = state.value.copy(
                                newsItems = mutableStateOf(news),
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }

}