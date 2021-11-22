package com.example.news.feature_news.data.repository

import com.example.news.core.util.Constants.Companion.TIME_DELAY
import com.example.news.core.util.Resource
import com.example.news.feature_news.data.local.ArticleDao
import com.example.news.feature_news.data.remote.NewsApi
import com.example.news.feature_news.domain.model.Article
import com.example.news.feature_news.domain.repository.NewsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class NewsRepositoryImpl(
    private val api: NewsApi,
    private val dao: ArticleDao,
): NewsRepository {

    override fun getEverythingNews(): Flow<Resource<List<Article>>> = flow {

        // 1 - show progress bar, we need to emit loading
        emit(Resource.Loading())

        var articles = emptyList<Article>()

        // 2 - get data from API
        try {
            val response = api.getEverythingNews()
            val remoteArticles = response.articles
            articles = remoteArticles.map { it.toArticle() }
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

        // 3 - show new data, we need to emit Success with our data from Api
        emit(Resource.Success(data = articles))

    }

    override fun getBreakingNews(): Flow<Resource<List<Article>>> = flow {

        // 1 - show progress bar, we need to emit loading
        emit(Resource.Loading())

        var articles = emptyList<Article>()
        var totalArticlesSize = 0
        println("total size start : $totalArticlesSize")

        while(true) {

            // 2 - get data from API
            try {
                val response = api.getBreakingNews()
                // 3 - show new data, we need to emit Success with our data from Api
                if(response.totalResults > totalArticlesSize) {
                    totalArticlesSize = response.totalResults
                    println("total size changed: $totalArticlesSize")
                    val remoteArticles = response.articles
                    articles = remoteArticles.map { it.toArticle() }
                    emit(Resource.Success(data = articles))
                } else {
                    println("no new breaking news...")
                }
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

            // 4 - delay 5 second before next request to api
            delay(TIME_DELAY)
        }

    }

    override suspend fun saveArticle(article: Article) {
        return dao.insertArticle(article.toArticleEntity())
    }

    override suspend fun deleteArticle(id: Int) {
        return dao.deleteArticle(id)
    }

    override fun getSavedArticles(): Flow<Resource<List<Article>>> = flow {

        // 1 - to show progress bar, we need to emit loading
        emit(Resource.Loading())

        // 2 - get articles from DataBase on android device
        val articles = dao.getArticles().map { it.toArticle() }

        // 3 - to show data, we need to emit loading with our data from DataBase
        emit(Resource.Success(data = articles))

    }

}