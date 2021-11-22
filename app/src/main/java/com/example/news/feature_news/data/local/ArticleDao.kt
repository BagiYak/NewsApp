package com.example.news.feature_news.data.local

import androidx.room.*
import com.example.news.feature_news.data.local.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    @Query("DELETE FROM articles WHERE id = :id")
    suspend fun deleteArticle(id: Int)

    @Query("SELECT * FROM articles")
    suspend fun getArticles(): List<ArticleEntity>

}