package com.uruklabs.newsspace.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uruklabs.newsspace.data.entites.database.PostDB
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAll(list: List<PostDB>)

    @Query("SELECT * FROM posts")
    fun getListPosts(): Flow<List<PostDB>>

    @Query("DELETE FROM posts")
    suspend fun clearDB()

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPost(id: Int): Flow<PostDB>
}
