package com.example.news.feature_news.di

import android.app.Application
import androidx.room.Room
import com.example.news.core.util.Constants
import com.example.news.feature_news.data.local.NewsDatabase
import com.example.news.feature_news.data.remote.NewsApi
import com.example.news.feature_news.data.repository.NewsRepositoryImpl
import com.example.news.feature_news.domain.repository.NewsRepository
import com.example.news.feature_news.domain.use_case.GetBreakingNews
import com.example.news.feature_news.domain.use_case.NewsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {

    @Provides
    @Singleton
    fun provideNewsUseCases(repository: NewsRepository): NewsUseCases {
        return NewsUseCases(
            getBreakingNews = GetBreakingNews(repository)
        )
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        db: NewsDatabase,
        api: NewsApi
    ): NewsRepository {
        return NewsRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(app: Application): NewsDatabase {
        return Room.databaseBuilder(
            app, NewsDatabase::class.java, "news_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
}