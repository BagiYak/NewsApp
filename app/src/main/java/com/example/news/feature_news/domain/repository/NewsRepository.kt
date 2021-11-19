package com.example.news.feature_news.domain.repository

import com.example.news.core.util.Resource
import com.example.news.feature_news.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getBreakingNews(): Flow<Resource<List<Article>>>

}