package com.example.data.data.data.dataSource.news

import com.example.domain.models.News

interface NewsOfflineDataSource  {
    suspend fun getNews(): List<News>
    suspend fun cacheNews(newsList:List<News>)
}