package com.uruklabs.newsspace.data.network

import com.uruklabs.newsspace.data.model.Launch

data class LaunchDTO(
    val id: String,
    val provider: String
) {
    fun toModel(): Launch = Launch(
        id = id,
        provider = provider
    )


}
fun Array<LaunchDTO>.toModel() : Array<Launch> = this.map {
    it.toModel()
}.toTypedArray()
