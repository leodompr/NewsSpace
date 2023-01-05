package com.uruklabs.newsspace.domain

import com.uruklabs.newsspace.core.UseCase
import com.uruklabs.newsspace.data.model.Post
import com.uruklabs.newsspace.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetLatestPostsUsecase(private val repository: PostRepository) : UseCase.NoParam<List<Post>>() {

    override suspend fun execute(): Flow<List<Post>> = repository.listPosts()

}