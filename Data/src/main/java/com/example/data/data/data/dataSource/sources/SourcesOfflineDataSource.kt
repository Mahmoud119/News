package com.example.data.data.data.dataSource.sources

import com.example.domain.models.Source

interface SourcesOfflineDataSource  {
    suspend fun getSources(categoryId: String): List<Source>
    suspend fun cacheSources(sourcesList: List<Source>)
}