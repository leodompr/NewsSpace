package com.uruklabs.newsspace.data.repository

import android.os.RemoteException
import com.uruklabs.newsspace.core.Resouce
import com.uruklabs.newsspace.data.dao.PostDao
import com.uruklabs.newsspace.data.entites.database.toModel
import com.uruklabs.newsspace.data.entites.model.Post
import com.uruklabs.newsspace.data.entites.network.toDB
import com.uruklabs.newsspace.data.entites.network.toModel
import com.uruklabs.newsspace.data.services.SpaceFightNewsServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException


class PostRepositoryImpl(private val service: SpaceFightNewsServices, private val dao: PostDao) :
    PostRepository {

    override suspend fun getlistPosts(category: String): Flow<Resouce<List<Post>>> =
        netWorkBoundResource(
            query = {
                dao.getListPosts().map {
                    it.sortedBy { post ->
                        post.publishedAt
                    }.reversed().toModel()
                }
            },
            fetch = { service.getListPosts(category) },
            saveFetchResult = {
                dao.clearDB()
                dao.saveAll(it.toDB())
            })


    override suspend fun getlistPostsByTitle(
        category: String,
        query: String?
    ): Flow<Resouce<List<Post>>> = netWorkBoundResource(
        query = {
            dao.getListPosts().map {
                it.sortedBy { post ->
                    post.publishedAt
                }.reversed().toModel()
            }
        },
        fetch = { service.getListPostsByTitle(type = category, query = query) },
        saveFetchResult = {
            dao.clearDB()
            dao.saveAll(it.toDB())
        })


    inline fun <ResultType, RequestType> netWorkBoundResource(
        crossinline query: () -> Flow<ResultType>,
        crossinline fetch: suspend () -> RequestType,
        crossinline saveFetchResult: suspend (RequestType) -> Unit
    ): Flow<Resouce<ResultType>> = flow {

        //consulta o banco de dados local
        var data = query().first()

        //consulta a api
        try {
            //se a api nao retorna vazio, limpa o cache local e salva os novos resultados
            saveFetchResult(fetch())
            data = query().first()

        } catch (ex: Exception) {
            val error = RemoteException("Error in connect, results view as cached")
            emit(Resouce.Error(data = data, error = error))
        }

        emit(Resouce.Success(data = data))

    }


}


