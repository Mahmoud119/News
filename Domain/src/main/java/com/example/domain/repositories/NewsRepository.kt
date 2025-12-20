package com.example.domain.repositories

import com.example.domain.models.News

interface NewsRepository {
    suspend fun getNews(sourceId: String): List<News>
}