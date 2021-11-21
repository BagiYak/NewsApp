package com.example.news.feature_news.domain.use_case

import com.example.news.feature_news.domain.model.Article
import com.example.news.feature_news.domain.repository.NewsRepository

class SaveArticle(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(article: Article) {
        return repository.saveArticle(article)
    }

}