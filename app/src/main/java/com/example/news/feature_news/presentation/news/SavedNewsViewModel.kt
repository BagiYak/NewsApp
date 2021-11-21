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
class SavedNewsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NewsState())
    val state: State<NewsState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var getJob: Job? = null

    init {
        onGetSavedNews()
    }

    fun onEvent(event: NewsEvent) {
        when (event) {
            is NewsEvent.GetSavedNews -> {
                onGetSavedNews()
            }
        }
    }

    private fun onGetSavedNews() {
        getJob?.cancel()

        getJob = viewModelScope.launch {
            newsUseCases.getArticles()
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                newsItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                newsItems = result.data ?: emptyList(),
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
                                    "Saved articles loading..."
                                )
                            )
                            _state.value = state.value.copy(
                                newsItems = result.data ?: emptyList(),
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