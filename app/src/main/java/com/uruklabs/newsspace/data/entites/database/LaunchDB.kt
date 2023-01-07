package com.uruklabs.newsspace.data.entites.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uruklabs.newsspace.data.entites.model.Launch

@Entity(tableName = "launch")
data class LaunchDB(
    @PrimaryKey
    val id: String,
    val provider: String
) {
    fun toModel(): Launch = Launch(
        id = id,
        provider = provider
    )


}

fun Array<LaunchDB>.toModel(): Array<Launch> = this.map {
    it.toModel()
}.toTypedArray()
