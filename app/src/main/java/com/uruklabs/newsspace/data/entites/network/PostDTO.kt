package com.uruklabs.newsspace.data.entites.network

import com.uruklabs.newsspace.data.entites.model.Launch
import com.uruklabs.newsspace.data.entites.model.Post

data class PostDTO(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String?,
    var launches: Array<LaunchDTO> = emptyArray()
) {
    fun toModel(): Post = Post(
        id = id,
        title = title,
        url = url,
        imageUrl = imageUrl,
        summary = summary,
        publishedAt = publishedAt,
        updatedAt = updatedAt,
        launches = launches.toModel()
    )
}

fun List<PostDTO>.toModel(): List<Post> = this.map {
    it.toModel()
}