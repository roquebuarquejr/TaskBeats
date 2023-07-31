package com.comunidadedevspace.taskbeats.data.remote

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val data: List<NewsDto>
)

//DTO - Data transfer object
data class NewsDto(
    @SerializedName("uuid")
    val id: String,
    @SerializedName("snippet")
    val content: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val title: String
)