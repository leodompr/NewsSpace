package com.uruklabs.newsspace.data.repository

import com.uruklabs.newsspace.core.Resouce
import com.uruklabs.newsspace.core.netWorkBoundResource
import com.uruklabs.newsspace.data.dao.PostDao
import com.uruklabs.newsspace.data.entites.database.toModel
import com.uruklabs.newsspace.data.entites.model.Post
import com.uruklabs.newsspace.data.entites.network.toDB
import com.uruklabs.newsspace.data.services.SpaceFightNewsServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class PostRepositoryImpl(private val service: SpaceFightNewsServices, private val dao: PostDao) :
    PostRepository {

    private val readDatabase = {
        dao.getListPosts().map {
            it.sortedBy { post ->
                post.publishedAt
            }.reversed().toModel()
        }
    }

    override suspend fun getlistPosts(category: String): Flow<Resouce<List<Post>>> =
        netWorkBoundResource(
            query = readDatabase,
            fetch = { service.getListPosts(category) },
            saveFetchResult = {
                dao.clearDB()
                dao.saveAll(it.toDB())
            })


    override suspend fun getlistPostsByTitle(
        category: String,
        query: String?
    ): Flow<Resouce<List<Post>>> = netWorkBoundResource(
        query = readDatabase,
        fetch = { service.getListPostsByTitle(type = category, query = query) },
        saveFetchResult = {
            dao.clearDB()
            dao.saveAll(it.toDB())
        })


}


