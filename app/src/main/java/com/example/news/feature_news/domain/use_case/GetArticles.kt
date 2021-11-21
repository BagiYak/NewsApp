package com.example.news.feature_news.domain.use_case

import com.example.news.core.util.Resource
import com.example.news.feature_news.domain.model.Article
import com.example.news.feature_news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetArticles(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<Resource<List<Article>>> {
        return repository.getSavedArticles()
    }

}