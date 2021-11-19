package com.example.news.feature_news.data.repository

import com.example.news.core.util.Resource
import com.example.news.feature_news.data.local.ArticleDao
import com.example.news.feature_news.data.remote.NewsApi
import com.example.news.feature_news.domain.model.Article
import com.example.news.feature_news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class NewsRepositoryImpl(
    private val api: NewsApi,
    private val dao: ArticleDao,
): NewsRepository {

    override fun getBreakingNews(): Flow<Resource<List<Article>>> = flow {
        // 1 - to show progress bar, we need to emit loading
        emit(Resource.Loading())

        // 2 - get articles from DataBase on android device
        val articles = dao.getArticles().map { it.toArticle() }

        // 3 - to show data, we need to emit loading with our data from DataBase
        emit(Resource.Loading(data = articles))

        // 4 - to get data from API
        try {
            val response = api.getBreakingNews()
            val remoteArticles = response.articles
            dao.deleteArticles(remoteArticles.map { it.toArticleEntity() })
            dao.insertArticles(remoteArticles.map { it.toArticleEntity() })
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "http error - while getting break news from api server",
                data = articles
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "IO error - while getting break news fro api server",
                data = articles
            ))
        }

        // 5 - get new articles from DataBase on android device
        val newArticles = dao.getArticles().map { it.toArticle() }

        // 6 - to show new data, we need to emit loading with our data from DataBase
        emit(Resource.Success(data = newArticles))

    }

}