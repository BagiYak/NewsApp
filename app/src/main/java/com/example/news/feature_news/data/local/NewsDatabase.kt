package com.example.news.feature_news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news.feature_news.data.local.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {

    abstract val dao: ArticleDao

}