package com.example.news.newsapp.common

data class ErrorState(
    val errorMessage: String? = null,
    val onRetry: (() -> Unit)? = null
)
