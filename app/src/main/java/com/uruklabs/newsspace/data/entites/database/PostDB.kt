package com.uruklabs.newsspace.data.entites.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uruklabs.newsspace.data.entites.model.Post
import com.uruklabs.newsspace.data.entites.network.PostDTO
import com.uruklabs.newsspace.data.entites.network.toModel

@Entity(tableName = "posts")
data class PostDB(
    @PrimaryKey
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String?,
    var launches: Array<LaunchDB> = emptyArray()
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
fun List<PostDB>.toModel(): List<Post> = this.map {
    it.toModel()
}
