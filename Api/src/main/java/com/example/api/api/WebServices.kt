package com.example.api.api

import com.example.api.model.NewsResponse.NewsResponse
import com.example.api.model.sourcesResponse.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
   suspend fun getSources(
        @Query("category")
        catId: String): SourcesResponse

    @GET("v2/everything")
    suspend fun getNews(
        @Query ("sources") source:String?
    ): NewsResponse
}