package com.uruklabs.newsspace.domain

import com.uruklabs.newsspace.core.UseCase
import com.uruklabs.newsspace.data.model.Post
import com.uruklabs.newsspace.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetLatestPostsUseCase(private val repository: PostRepository) :
    UseCase<String, List<Post>>() {
    override suspend fun execute(param: String): Flow<List<Post>> = repository.getlistPosts(param)

}