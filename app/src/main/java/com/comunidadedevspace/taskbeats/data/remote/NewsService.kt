package com.comunidadedevspace.taskbeats.data.remote

import retrofit2.http.GET

interface NewsService {

    //https://inshorts.deta.dev/news?category=science
    @GET("news?category=science")
    suspend fun fetchNews(): NewsResponse

}