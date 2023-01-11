package com.uruklabs.newsspace.data.repository

import com.uruklabs.newsspace.core.Resouce
import com.uruklabs.newsspace.data.entites.model.Post
import kotlinx.coroutines.flow.Flow

/**
 * Essa interface abstrai a implementação de um repositório para
 * os objetos do tipo Post.
 */
interface PostRepository {

    fun getlistPosts(category: String): Flow<Resouce<List<Post>>>

    fun getlistPostsByTitle(category: String, query: String?): Flow<Resouce<List<Post>>>
}
