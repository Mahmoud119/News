package com.example.data.data.data.dataSource.news

import com.example.domain.models.News

interface NewsOnlineDataSource  {
    suspend fun getNews(sourceId: String): List<News>
}