package com.example.news.newsapp.api.model.NewsResponse

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class NewsResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val newsList: List<News?>? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable