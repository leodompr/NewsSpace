package com.uruklabs.newsspace.data.services

import com.uruklabs.newsspace.data.model.Post
import retrofit2.http.GET

interface SpaceFightNewsServices {

    @GET("articles")
    suspend fun listPosts() : List<Post>

}