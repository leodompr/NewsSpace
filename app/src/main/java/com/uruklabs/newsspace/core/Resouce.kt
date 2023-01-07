package com.uruklabs.newsspace.core

sealed class Resouce<T>(
    open val data: T?,
    open val error: Throwable? = null
) {

    data class Success<T>(override val data: T?) : Resouce<T>(data, null)
    data class Error<T>(override val data: T?, override val error: Throwable) :
        Resouce<T>(null, error)

}
