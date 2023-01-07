package com.uruklabs.newsspace.data.services

import com.uruklabs.newsspace.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceFightNewsServices {

//    @GET("articles")
//    suspend fun listPosts() : List<Post>

    @GET("{type}")
    suspend fun getListPosts(@Path("type") type : String) : List<Post>

}