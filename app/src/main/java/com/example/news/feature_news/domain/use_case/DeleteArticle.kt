package com.example.news.feature_news.domain.use_case

import com.example.news.feature_news.domain.repository.NewsRepository

class DeleteArticle(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(id: Int) {
        return repository.deleteArticle(id)
    }

}