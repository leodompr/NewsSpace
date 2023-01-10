package com.uruklabs.newsspace.core

import android.os.RemoteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

inline fun <ResultType, RequestType> netWorkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit
): Flow<Resouce<ResultType>> = flow {

    // consulta o banco de dados local
    var data = query().first()

    // consulta a api
    try {
        // se a api nao retorna vazio, limpa o cache local e salva os novos resultados
        saveFetchResult(fetch())
        data = query().first()
    } catch (ex: Exception) {
        val error = RemoteException("Error in connect, results view as cached")
        emit(Resouce.Error(data = data, error = error))
    }

    emit(Resouce.Success(data = data))
}
