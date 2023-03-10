package com.uruklabs.newsspace.domain

import com.uruklabs.newsspace.core.Query
import com.uruklabs.newsspace.core.Resouce
import com.uruklabs.newsspace.core.UseCase
import com.uruklabs.newsspace.data.entites.model.Post
import com.uruklabs.newsspace.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetLatestPostsUseCase(private val repository: PostRepository) :
    UseCase<Query, Resouce<List<Post>>>() {
    override fun execute(param: Query): Flow<Resouce<List<Post>>> =
        repository.getlistPosts(param.type)
}
