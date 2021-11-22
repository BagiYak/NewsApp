package com.example.news.feature_news.presentation.news

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.core.util.Constants
import com.example.news.core.util.Resource
import com.example.news.feature_news.domain.model.Article
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
class NewsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NewsState())
    val state: State<NewsState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val page = mutableStateOf(1)
    private var listScrollPosition = 0

    var getNewsJob: Job? = null

    init {
        onGetNews(NewsEndpoint.EverythingNews, page = 1)
    }

    fun onEvent(event: NewsEvent) {
        when (event) {
            is NewsEvent.GetNews -> {
                onGetNews(event.newsEndpoint, page = 1)
            }
        }
    }

    private fun onGetNews(newsEndpoint: NewsEndpoint, page: Int) {
        getNewsJob?.cancel()

        getNewsJob = viewModelScope.launch {
            newsUseCases.getNews(newsEndpoint, page)
                .onEach { result ->
                    val news = result.data ?: emptyList()
                    when(result) {
                        is Resource.Success -> {
                            appendNews(news)
                            _state.value = state.value.copy(
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            appendNews(news)
                            _state.value = state.value.copy(
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
                                    "News loading..."
                                )
                            )
                            appendNews(news)
                            _state.value = state.value.copy(
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }


    fun nextPage() {
        viewModelScope.launch {
            if((listScrollPosition + 1) >= (page.value * Constants.QUERY_PAGE_SIZE)) {
                incrementPage()

                if(page.value > 1) {
                    onGetNews(NewsEndpoint.EverythingNews, page.value)
                }
            }
        }
    }

    private fun appendNews(news: List<Article>) {
        val current = ArrayList(state.value.newsItems.value)
        current.addAll(news)
        state.value.newsItems.value = current
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeListScrollPosition(position: Int) {
        listScrollPosition = position
        println("scroll position: $position")
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }

}