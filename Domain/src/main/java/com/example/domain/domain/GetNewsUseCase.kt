package com.example.domain.domain

import com.example.domain.models.News
import com.example.domain.repositories.NewsRepository

class GetNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun invoke(sourceId: String): List<News>{
            val newslist=newsRepository.getNews(sourceId =sourceId )
        return newslist
    }
}