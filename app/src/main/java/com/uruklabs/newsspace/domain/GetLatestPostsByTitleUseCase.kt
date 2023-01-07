package com.uruklabs.newsspace.domain

import com.uruklabs.newsspace.core.Query
import com.uruklabs.newsspace.core.UseCase
import com.uruklabs.newsspace.data.entites.model.Post
import com.uruklabs.newsspace.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetLatestPostsByTitleUseCase(private val repository: PostRepository) :
    UseCase<Query, List<Post>>() {

    override suspend fun execute(param: Query): Flow<List<Post>> =
        repository.getlistPostsByTitle(query = param.query, category = param.type)

}