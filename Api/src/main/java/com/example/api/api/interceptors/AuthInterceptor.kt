package com.example.api.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
     val apiKey="92173ea6aa234f7d8962400a980f48ff" 
       val newRequestBuilder= chain.request().newBuilder()
        newRequestBuilder.addHeader("X-Api-Key",apiKey)
            .build()

        return chain.proceed(newRequestBuilder.build())
    }
}