package com.example.data.data.data.repos

import com.example.data.data.data.dataSource.news.NewsOfflineDataSource
import com.example.data.data.data.dataSource.news.NewsOnlineDataSource
import com.example.domain.models.News

class NewsRepoImpl (private val newsOnlineDataSource: NewsOnlineDataSource,
                    private val newsOfflineDataSource: NewsOfflineDataSource): com.example.domain.repositories.NewsRepository {
    override suspend fun getNews(sourceId: String): List<News> {
       val news = newsOnlineDataSource.getNews(sourceId)
    }


}