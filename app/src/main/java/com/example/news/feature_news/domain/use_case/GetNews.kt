package com.example.news.feature_news.domain.use_case

import com.example.news.core.util.Resource
import com.example.news.feature_news.domain.model.Article
import com.example.news.feature_news.domain.repository.NewsRepository
import com.example.news.feature_news.presentation.news.NewsEndpoint
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val repository: NewsRepository
) {

    operator fun invoke(
        newsEndpoint: NewsEndpoint = NewsEndpoint.BreakingNews,
        page: Int = 1,
    ): Flow<Resource<List<Article>>> {
        return when(newsEndpoint) {
            is NewsEndpoint.BreakingNews -> repository.getBreakingNews()
            is NewsEndpoint.EverythingNews -> repository.getEverythingNews(page)
        }
    }

}