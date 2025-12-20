package com.example.api.dataSource

import com.example.api.api.ApiManager
import com.example.api.model.NewsResponse.NewsDTO
import com.example.data.data.data.dataSource.news.NewsOnlineDataSource
import com.example.domain.models.News

class NewsOnlineDataSourceImpl: NewsOnlineDataSource
{
    override suspend fun getNews(sourceId: String): List<News> {
            val response = ApiManager.webServices().getNews(sourceId)
        val newsList=response.newsList?.filterNotNull()?.map { newsdTO : NewsDTO -> newsdTO.toNews() }?.toList()
        return newsList?: listOf()
    }
}