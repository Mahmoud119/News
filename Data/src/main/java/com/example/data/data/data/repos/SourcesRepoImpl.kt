package com.example.data.data.data.repos

import com.example.data.data.data.dataSource.sources.SourcesOfflineDataSource
import com.example.data.data.data.dataSource.sources.SourcesOnlineDataSource
import com.example.domain.models.Source
import com.example.domain.repositories.SourcesRepository

class SourcesRepoImpl (private val sourcesOnlineDataSource: SourcesOnlineDataSource ,
                       private val sourcesOfflineDataSource: SourcesOfflineDataSource): SourcesRepository {
    override suspend fun getSources(categoryId: String): List<Source> {
/*        if(online){
            val respone = sourcesOnlineDataSource.getSources(categoryId)
            sourcesOfflineDataSource.cacheSources(respone)
            return respone
        }else{
                return sourcesOfflineDataSource.getSources(categoryId)
        }*/
        val respone = sourcesOnlineDataSource.getSources(categoryId)
        return respone
    }

}