package com.example.news.newsapp.ui.news

import android.R.attr.category
import android.R.id.message
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.newsapp.api.ApiManager
import com.example.news.newsapp.common.ErrorState
import com.example.news.newsapp.model.Category
import com.example.news.newsapp.model.ErrorResponse
import com.example.news.newsapp.model.NewsResponse.News
import com.example.news.newsapp.model.NewsResponse.NewsResponse
import com.example.news.newsapp.model.sourcesResponse.Source
import com.example.news.newsapp.model.sourcesResponse.SourcesResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    val showLoading = MutableLiveData<Boolean>(false)
    val errorState = MutableLiveData<ErrorState>()
    val sourcesLiveData = MutableLiveData<List<Source?>?>()
    val newsLiveData = MutableLiveData<List<News?>?>()
    fun loadSources(categoryId: String) {

        // loading status
        showLoading.value = true
        ApiManager.webServices().getSources(categoryId)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse( // any request from the server
                    call: Call<SourcesResponse?>,
                    response: Response<SourcesResponse?>
                ) {
                    if (!response.isSuccessful) {
                        Log.d(
                            TAG,
                            "onResponse: response.isSuccessful : ${response.isSuccessful.toString()}"
                        )
                        showLoading.value = false
                        //handl error view
                        val errorBody = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        val message = errorResponse?.message ?: "Something Went Wrong"

                        errorState.value = ErrorState(
                            errorMessage = message,
                            onRetry = {
                                loadSources(categoryId)
                            })
                        return
                    }
                    // suecess you have response with sources
                    //show sources
                    sourcesLiveData.value = response.body()?.sources
                }


                override fun onFailure( // couldn't connect to the server
                    call: Call<SourcesResponse?>,
                    error: Throwable
                ) {
                    Log.d(TAG, "onFailure: ")
                    errorState.value = ErrorState(
                        errorMessage = error.localizedMessage ?: "Something Went Wrong",
                        onRetry = {
                            loadSources(categoryId)
                        })
                }


            }
            )
    }

    fun loadNews(sourceId: String) {
        showLoading.value = true
        ApiManager.webServices().getNews(source = sourceId)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse?>,
                    response: Response<NewsResponse?>
                ) {
                    if (!response.isSuccessful) {
                        Log.d(
                            TAG,
                            "onResponse: response.isSuccessful : ${response.isSuccessful.toString()}"
                        )
                        showLoading.value = false
                        //handl error view
                        val errorBody = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        val message = errorResponse?.message ?: "Something Went Wrong"
                        errorState.value = ErrorState(
                            errorMessage = message,
                            onRetry = {
                                loadNews(sourceId)
                            })
                        return
                    }
                    showLoading.value = false
                    newsLiveData.value = response.body()?.newsList

                }


                override fun onFailure(
                    call: Call<NewsResponse?>,
                    error: Throwable
                ) {
                    Log.d(TAG, "onFailure: ")
                    errorState.value = ErrorState(
                        errorMessage = error.localizedMessage ?: "Something Went Wrong",
                        onRetry = {
                            loadNews(sourceId)
                        })
                }
            }
            )
    }
}