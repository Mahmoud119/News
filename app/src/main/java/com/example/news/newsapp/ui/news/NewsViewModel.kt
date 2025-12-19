package com.example.news.newsapp.ui.news

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.newsapp.api.ApiManager
import com.example.news.newsapp.common.ErrorState
import com.example.news.newsapp.model.ErrorResponse
import com.example.news.newsapp.model.News
import com.example.news.newsapp.model.sourcesResponse.Source
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NewsViewModel : ViewModel() {
    val showLoading = MutableLiveData<Boolean>(false)
    val errorState = MutableLiveData<ErrorState>()
    val sourcesLiveData = MutableLiveData<List<Source?>?>()
    val newsLiveData = MutableLiveData<List<News?>?>()
    fun loadSources(categoryId: String) {

        // loading status
        showLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiManager.webServices().getSources(categoryId)
                sourcesLiveData.postValue(response.sources)
            } catch (exception: Exception) {
                Log.d(TAG, "onFailure: ")
                errorState.postValue(
                    ErrorState(
                        errorMessage = exception.localizedMessage ?: "Something Went Wrong",
                        onRetry = {
                            loadSources(categoryId)
                        })
                )
            } catch (httpException: HttpException) {
                //handl error view
                val errorResponse = extractError(httpException)
                val message = errorResponse.message ?: "Something Went Wrong"

                errorState.value = ErrorState(
                    errorMessage = message,
                    onRetry = {
                        loadSources(categoryId)
                    })
            }
        }

    }
    fun loadNews(sourceId: String) {
        showLoading.value = true
        try {
            viewModelScope.launch {
                val response = ApiManager.webServices().getNews(source = sourceId)
                showLoading.value = false
                newsLiveData.value = response.newsList
            }
        } catch (exception: Exception) {
            Log.d(TAG, "onFailure: ")
            errorState.postValue(
                ErrorState(
                    errorMessage = exception.localizedMessage ?: "Something Went Wrong",
                    onRetry = {
                        loadNews(sourceId)
                    })
            )
        } catch (httpException: HttpException) {
            val errorResponse = extractError(httpException)
            val message = errorResponse.message ?: "Something Went Wrong"

            errorState.value = ErrorState(
                errorMessage = message,
                onRetry = {
                    loadNews(sourceId)
                })
        }

    }
    private fun extractError(httpException: HttpException): ErrorResponse {
        val errorBody = httpException.response()?.errorBody()?.string()
        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
        return errorResponse
    }

}