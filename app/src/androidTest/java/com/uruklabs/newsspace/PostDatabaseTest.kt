package com.uruklabs.newsspace

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uruklabs.newsspace.data.dao.PostDao
import com.uruklabs.newsspace.data.dao.databse.PostDatabase
import com.uruklabs.newsspace.data.entites.database.LaunchDB
import com.uruklabs.newsspace.data.entites.database.PostDB
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PostDatabaseTest {

    private lateinit var listDbPosts: List<PostDB>

    @Before
    fun createPostsForTests() {

        val postDB = PostDB(
            id = 12782,
            title = "Crew-3 mission cleared for launch",
            url = "https://spacenews.com/crew-3-mission-cleared-for-launch/",
            imageUrl = "https://spacenews.com/wp-content/uploads/2021/11/crew2-chutes.jpg",
            summary = "NASA and SpaceX are ready to proceed with the launch of a commercial crew mission Nov. 10 after overcoming weather and astronaut health issues as well as concerns about the spacecraft’s parachutes.",
            publishedAt = "2021-11-10T09:27:02.000Z",
            updatedAt = "2021-11-10T09:38:23.654Z",
            launches = emptyArray()
        )

        val postDBWithLaunch = PostDB(
            id = 12793,
            title = "Crew-4 mission cleared for launch",
            url = "https://spacenews.com/crew-3-mission-cleared-for-launch/",
            imageUrl = "https://spacenews.com/wp-content/uploads/2021/11/crew2-chutes.jpg",
            summary = "NASA and SpaceX are ready to proceed with the launch of a commercial crew mission Nov. 10 after overcoming weather and astronaut health issues as well as concerns about the spacecraft’s parachutes.",
            publishedAt = "2021-11-10T09:27:02.000Z",
            updatedAt = "2021-11-10T09:38:23.654Z",
            launches = arrayOf(
                LaunchDB(
                    id = "00254545454544",
                    provider = "Lauch Test"
                )
            )
        )

        listDbPosts = listOf(postDB, postDBWithLaunch)

    }

    private lateinit var dao: PostDao
    private lateinit var postDatabase: PostDatabase


    @Before
    fun createDB() {
        //instanciar o PostDataBase em memória
        val context = ApplicationProvider.getApplicationContext<Context>()
        postDatabase = Room.inMemoryDatabaseBuilder(
            context,
            PostDatabase::class.java
        ).build()

        dao = postDatabase.dao
    }


    @After
    @Throws(IOException::class)
    fun closeDB() {
        postDatabase.close()
    }

    @Test
    fun deve_GravarPostsNoBancoDeDadosLocal_AoReceberListaDePosts() {
        //verificando se lista não esta vazia
        lateinit var result: List<PostDB>
        runBlocking {
            result = dao.getListPosts().first()
        }
        assertTrue(result.isEmpty())

        //salvando os dados no Database
        runBlocking {
            dao.saveAll(listDbPosts)
            result = dao.getListPosts().first()
        }

        //verifando se dao não esta vazio
        assertTrue(result.isNotEmpty())

    }

    @Test
    fun deve_RetornarPostsCorretamente_AoLerBancoDeDadosLocal() {
        lateinit var result: PostDB
        runBlocking {
            dao.saveAll(listDbPosts)
            result = dao.getListPosts().first()[0]
        }
        assertTrue(result.title == listDbPosts.first().title)
    }


    @Test
    fun deve_LimparBancoDeDadosLocal_AoChamarFuncaoCorrespondente() {
        lateinit var result: List<PostDB>
        runBlocking {
            dao.saveAll(listDbPosts)
            dao.clearDB()
            result = dao.getListPosts().first()
        }
        assertTrue(result.isEmpty())
    }

}