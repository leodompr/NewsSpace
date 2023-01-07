package com.uruklabs.newsspace.data.dao.databse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.uruklabs.newsspace.data.dao.PostDao
import com.uruklabs.newsspace.data.entites.database.LaunchDB
import com.uruklabs.newsspace.data.entites.database.PostDB
import com.uruklabs.newsspace.data.entites.database.PostDBConverters

@Database(entities = [PostDB::class, LaunchDB::class], version = 1, exportSchema = false)
@TypeConverters(PostDBConverters::class)
abstract class PostDatabase : RoomDatabase() {
    abstract val dao: PostDao

    companion object {

        @Volatile
        private var INSTANCE: PostDatabase? = null

        fun getInstance(context: Context): PostDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PostDatabase::class.java,
                        "posts_cache"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }


}