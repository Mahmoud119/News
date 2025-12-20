package com.example.api.model.NewsResponse

import com.example.api.model.sourcesResponse.SourceDTO
import com.example.domain.models.News
import com.google.gson.annotations.SerializedName



data class NewsDTO(

    @field:SerializedName("publishedAt")
	val publishedAt: String? = null,

    @field:SerializedName("author")
	val author: String? = null,

    @field:SerializedName("urlToImage")
	val urlToImage: String? = null,

    @field:SerializedName("description")
	val description: String? = null,

    @field:SerializedName("source")
	val source: SourceDTO? = null,

    @field:SerializedName("title")
	val title: String? = null,

    @field:SerializedName("url")
	val url: String? = null,

    @field:SerializedName("content")
	val content: String? = null
){
    fun toNews()= News(
        title = title,
        author = author,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt, description = description,
        content = content
    )
}