package com.uruklabs.newsspace.data.entites.network

import com.uruklabs.newsspace.data.entites.database.LaunchDB
import com.uruklabs.newsspace.data.entites.model.Launch

data class LaunchDTO(
    val id: String,
    val provider: String
) {
    fun toModel(): Launch = Launch(
        id = id,
        provider = provider
    )

    fun toDB(): LaunchDB = LaunchDB(
        id = id,
        provider = provider
    )


}
fun Array<LaunchDTO>.toModel() : Array<Launch> = this.map {
    it.toModel()
}.toTypedArray()

fun Array<LaunchDTO>.toDB() : Array<LaunchDB> = this.map {
    it.toDB()
}.toTypedArray()
