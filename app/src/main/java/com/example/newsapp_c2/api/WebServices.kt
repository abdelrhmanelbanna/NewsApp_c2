package com.example.newsapp_c2.api

import com.example.newsapp_c2.model.NewsResponse
import com.example.newsapp_c2.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
    fun getSources(
      @Query("apiKey") apiKeys:String
    ):Call<SourcesResponse>


    @GET("v2/everything")
    fun getNews(
        @Query("apiKey") apiKeys:String,
        @Query("sources") sources:String
    ):Call<NewsResponse>


}