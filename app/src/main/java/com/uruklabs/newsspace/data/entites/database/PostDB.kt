package com.uruklabs.newsspace.data.entites.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.uruklabs.newsspace.data.entites.model.Post
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

class PostDBConverters {

    /**
     Conversor de Launch para String para salvar no Room e
     depois converter de volta essa string Json para a DataClass
     **/

    @TypeConverter
    fun toString(array: Array<LaunchDB>): String? {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(Array<LaunchDB>::class.java)
        return jsonAdapter.toJson(array)
    }

    @TypeConverter
    fun fromString(string: String): Array<LaunchDB>? {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(Array<LaunchDB>::class.java)
        return jsonAdapter.fromJson(string)
    }
}
