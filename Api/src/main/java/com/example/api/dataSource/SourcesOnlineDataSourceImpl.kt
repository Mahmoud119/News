package com.example.api.dataSource

import com.example.api.api.ApiManager
import com.example.api.model.sourcesResponse.SourceDTO
import com.example.data.data.data.dataSource.sources.SourcesOnlineDataSource
import com.example.domain.models.Source

class SourcesOnlineDataSourceImpl : SourcesOnlineDataSource{
    override suspend fun getSources(categoryId: String): List<Source> {
       val response= ApiManager.webServices().getSources(categoryId)
        val sources =response.sources?.filterNotNull()?.map { dto -> dto.toSource() }?.toList()
        return sources ?:listOf()
    }
}