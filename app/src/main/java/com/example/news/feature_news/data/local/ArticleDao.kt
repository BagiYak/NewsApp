package com.example.news.feature_news.data.local

import androidx.room.*
import com.example.news.feature_news.data.local.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articles")
    suspend fun getArticles(): List<ArticleEntity>

    @Query("UPDATE articles SET favorite = 1 WHERE id = :articleId")
    suspend fun saveFavoriteArticle(articleId: Long)

    @Query("SELECT * FROM articles WHERE favorite = 1 ORDER BY id DESC")
    suspend fun getFavoriteArticles(): List<ArticleEntity>

    @Delete
    suspend fun deleteArticles(articles: List<ArticleEntity>)

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)
}