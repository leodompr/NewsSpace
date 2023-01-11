package com.uruklabs.newsspace.core

import kotlinx.coroutines.flow.Flow

abstract class UseCase<Param, Source> {

    abstract fun execute(param: Param): Flow<Source>

    // Por meio do "operator" posso chamar uma função usando o nome do meu objeto
    suspend operator fun invoke(param: Param) = execute(param)

    abstract class NoParam<Source> : UseCase<Nothing, Source>() {
        abstract suspend fun execute(): Flow<Source>

        final override fun execute(param: Nothing): Flow<Source> {
            throw UnsupportedOperationException()
        }

        suspend operator fun invoke() = execute()
    }
}
