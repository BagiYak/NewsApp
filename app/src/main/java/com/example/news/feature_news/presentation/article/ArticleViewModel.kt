package com.example.news.feature_news.presentation.article

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.feature_news.domain.model.Article
import com.example.news.feature_news.domain.use_case.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ArticleState())
    val state: MutableState<ArticleState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var daoJob: Job? = null

    fun onEvent(event: ArticleEvent) {
        when (event) {
            is ArticleEvent.SaveArticle -> {
                (state.value.article)?.let { onSaveArticle(it) }
            }
        }
    }

    private fun onSaveArticle(article: Article) {
        daoJob?.cancel()

        daoJob = viewModelScope.launch {
            try {
                newsUseCases.saveArticle(article)
                _eventFlow.emit(UIEvent.SaveArticle)
            } catch(e: Exception) {
                _eventFlow.emit(
                    UIEvent.ShowSnackbar(
                        message = e.message ?: "Couldn't save article"
                    )
                )
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
        object SaveArticle: UIEvent()
    }

}