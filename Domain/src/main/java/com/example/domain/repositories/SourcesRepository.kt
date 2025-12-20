package com.example.domain.repositories

import com.example.domain.models.Source

interface SourcesRepository {
    suspend fun getSources(categoryId: String): List<Source>
}