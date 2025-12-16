package com.example.news.newsapp.api

import com.example.news.newsapp.api.model.NewsResponse.NewsResponse
import com.example.news.newsapp.api.model.sourcesResponse.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
    fun getSources(
    ): Call<SourcesResponse>

    @GET("v2/everything")
    fun getNews(
        @Query ("sources") source:String?
    ): Call<NewsResponse>
}