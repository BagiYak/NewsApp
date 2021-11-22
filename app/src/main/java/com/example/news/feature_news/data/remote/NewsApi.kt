package com.example.news.feature_news.data.remote

import com.example.news.core.util.Constants.Companion.API_KEY
import com.example.news.core.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.news.feature_news.data.remote.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    // endpoint api to get 'everything' from NewsApi service
    @GET("v2/everything")
    suspend fun getEverythingNews(
        @Query("q")
        theme: String = "apple",
        @Query("pageSize")
        pageSize: Int = QUERY_PAGE_SIZE,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse

    // endpoint api to get 'top of headlines' from NewsApi service
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("q")
        theme: String = "apple",
        @Query("pageSize")
        pageSize: Int = QUERY_PAGE_SIZE,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse

}