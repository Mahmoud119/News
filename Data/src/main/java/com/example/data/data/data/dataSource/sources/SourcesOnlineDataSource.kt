package com.example.data.data.data.dataSource.sources

import com.example.domain.models.Source

interface SourcesOnlineDataSource  {
    suspend fun getSources(categoryId: String): List<Source>
}