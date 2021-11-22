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
                (state.value.article)?.let {
                    onSaveArticle(it)
                }
            }
            is ArticleEvent.DeleteArticle -> {
                println("article: ${state.value.article}")
                (state.value.article)?.let {
                    it.id?.let { id -> onDeleteArticle(id) }
                    println("deleted article init: id = ${it.id}")
                }
            }
        }
    }

    private fun onSaveArticle(article: Article) {
        daoJob?.cancel()

        daoJob = viewModelScope.launch {
            try {
                newsUseCases.saveArticle(article)
                _eventFlow.emit(UIEvent.SaveArticle)
                _state.value = state.value.copy(
                    article = article
                )
            } catch(e: Exception) {
                _eventFlow.emit(
                    UIEvent.ShowSnackbar(
                        message = e.message ?: "Couldn't save article"
                    )
                )
            }
        }
    }

    private fun onDeleteArticle(id: Int) {
        println("delete id: $id")
        daoJob?.cancel()

        daoJob = viewModelScope.launch {
            try {
                newsUseCases.deleteArticle(id)
                _eventFlow.emit(UIEvent.DeleteArticle)
                _state.value = state.value.copy(
                    article = null
                )
                println("deleted article")
            } catch(e: Exception) {
                _eventFlow.emit(
                    UIEvent.ShowSnackbar(
                        message = e.message ?: "Couldn't delete article"
                    )
                )
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
        object SaveArticle: UIEvent()
        object DeleteArticle: UIEvent()
    }

}