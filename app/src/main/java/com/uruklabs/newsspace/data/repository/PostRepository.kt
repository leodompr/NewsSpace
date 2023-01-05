package com.uruklabs.newsspace.data.repository

import com.uruklabs.newsspace.data.model.Post
import kotlinx.coroutines.flow.Flow

/**
 * Essa interface abstrai a implementação de um repositório para
 * os objetos do tipo Post.
 */
interface PostRepository {

    suspend fun listPosts(category: String): Flow<List<Post>>
}