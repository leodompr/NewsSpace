package com.uruklabs.newsspace.domain

import com.uruklabs.newsspace.core.UseCase
import com.uruklabs.newsspace.data.model.Post
import com.uruklabs.newsspace.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetLatestPostsByTitleUseCase(private val repository: PostRepository) :
    UseCase<Array<String>, List<Post>>() {

    override suspend fun execute(param: Array<String>): Flow<List<Post>> = repository.getlistPostsByTitle(param.first(), param[1])

}