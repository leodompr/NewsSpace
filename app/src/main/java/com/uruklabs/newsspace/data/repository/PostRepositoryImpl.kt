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
import retrofit2.HttpException


class PostRepositoryImpl(private val service: SpaceFightNewsServices, private val dao: PostDao) :
    PostRepository {

    override suspend fun getlistPosts(category: String): Flow<Resouce<List<Post>>> =
        netWorkBoundResource(category)

    override suspend fun getlistPostsByTitle(category: String, query: String?): Flow<List<Post>> {
        return flow {

            try {
                val postList = service.getListPostsByTitle(category, query).toModel()
                emit(postList)
            } catch (e: HttpException) {
                throw RemoteException("Unable to conncet to SpaceFlight News Api")
            }

        }

    }

    private fun netWorkBoundResource(category: String): Flow<Resouce<List<Post>>> = flow {

        //consulta o banco de dados local
        var data = dao.getListPosts().first()

        //consulta a api
        try {
            //se a api nao retorna vazio, limpa o cache local e salva os novos resultados
            with(service.getListPosts(category)) {
                if (this.isNotEmpty()) {
                    dao.clearDB()
                    dao.saveAll(this.toDB())
                }
                data = dao.getListPosts().first()
            }
        } catch (ex: Exception) {
            val error = RemoteException("Error in connect, results view as cached")
            emit(Resouce.Error(data = data.toModel(), error = error))
        }

        emit(Resouce.Success(data = data.toModel()))

    }


}


