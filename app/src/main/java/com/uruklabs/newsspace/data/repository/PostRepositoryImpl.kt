package com.uruklabs.newsspace.data.repository

import android.os.RemoteException
import com.uruklabs.newsspace.data.model.Post
import com.uruklabs.newsspace.data.network.toModel
import com.uruklabs.newsspace.data.services.SpaceFightNewsServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException


class PostRepositoryImpl(private val service: SpaceFightNewsServices) : PostRepository {

    override suspend fun getlistPosts(category: String): Flow<List<Post>> = flow {

        try {
            val postList = service.getListPosts(category).toModel()
            emit(postList)
        } catch (e: HttpException) {
            throw RemoteException("Unable to conncet to SpaceFlight News Api")
        }

    }

    override suspend fun getlistPostsByTitle(category: String, query: String?): Flow<List<Post>> =
        flow {

            try {
                val postList = service.getListPostsByTitle(category, query).toModel()
                emit(postList)
            } catch (e: HttpException) {
                throw RemoteException("Unable to conncet to SpaceFlight News Api")
            }

        }
}


