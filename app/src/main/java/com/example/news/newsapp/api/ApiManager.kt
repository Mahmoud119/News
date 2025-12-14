package com.example.news.newsapp.api


import android.R.id.message
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {
  companion object  { //static
     private var retrofit: Retrofit? = null

      private fun initRetrofit(): Retrofit{
          if (retrofit==null){
              val loggingInterceptor = HttpLoggingInterceptor { message: String ->
                  Log.e("api", message)
              }
              loggingInterceptor.level= HttpLoggingInterceptor.Level.BODY
              val okHttpClient = OkHttpClient.Builder()
                  .addInterceptor(loggingInterceptor).build()
              retrofit = Retrofit.Builder()
                  .client(okHttpClient)
                  .baseUrl("https://newsapi.org/")
                  .addConverterFactory(GsonConverterFactory.create())
                  .build()
          }
          return retrofit!!
      }
      fun webServices(): WebServices{
          return initRetrofit().create(WebServices::class.java)
      }
  }
}