package com.example.domain.domain

import com.example.domain.models.Source
import com.example.domain.repositories.SourcesRepository

class GetSourcesUseCase (private val sourcesRepository: SourcesRepository){
    suspend fun getSources(categoryId:String): List<Source>{
        val sources= sourcesRepository.getSources(categoryId)
        return sources
    }
}