package com.uruklabs.newsspace.domain

import com.uruklabs.newsspace.core.Query
import com.uruklabs.newsspace.core.Resouce
import com.uruklabs.newsspace.data.SpaceFlightNewsCategory
import com.uruklabs.newsspace.data.entites.model.Post
import com.uruklabs.newsspace.data.repository.PostRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetLatestPostsUsecaseTest {

    private val repository: PostRepository = mockk()
    private lateinit var getLatestPostsUsecase: GetLatestPostsUseCase
    private val listMock: Resouce<List<Post>> = Resouce.Success(
        data = listOf(
            Post(
                id = 12782,
                title = "Crew-3 mission cleared for launch",
                url = "https://spacenews.com/crew-3-mission-cleared-for-launch/",
                imageUrl = "https://spacenews.com/wp-content/uploads/2021/11/crew2-chutes.jpg",
                summary = "NASA and SpaceX are ready to proceed with the" +
                    " launch of a commercial crew mission Nov." +
                    " 10 after overcoming weather and astronaut health issues as well" +
                    " as concerns about the spacecraft’s parachutes.",
                publishedAt = "2021-11-10T09:27:02.000Z",
                updatedAt = "2021-11-10T09:38:23.654Z",
                launches = arrayOf()
            )
        )
    )

    @Before
    fun setup() {
        getLatestPostsUsecase = GetLatestPostsUseCase(repository)
        every { repository.getlistPosts(SpaceFlightNewsCategory.ARTICLES.value) }.returns(
            flowOf(listMock)
        )
    }

    @Test
    fun `deve retornar resultado não nulo ao chamar o com repositorio`() {
        runBlocking {
            val result = getLatestPostsUsecase(Query(type = SpaceFlightNewsCategory.ARTICLES.value))
            assertNotNull(result)
        }
    }

    @Test
    fun `deve retornar resultado do tipo esperado ao chamar o repositorio`() {
        runBlocking {
            val result = getLatestPostsUsecase(Query(type = SpaceFlightNewsCategory.ARTICLES.value))

            assertTrue(result is Flow<Resouce<List<Post>>>)
        }
    }

    @Test
    fun `deve retornar não vazio ao ao chamar o repositorio`() {
        runBlocking {
            val result = getLatestPostsUsecase(Query(type = SpaceFlightNewsCategory.ARTICLES.value))
            assertFalse(result.first().data!!.isEmpty())
        }
    }

    @Test
    fun `deve retornar objeto correto ao chamar o repositorio`() {
        runBlocking {
            val result = getLatestPostsUsecase(Query(type = SpaceFlightNewsCategory.ARTICLES.value))
            val firtsElement = result.first().data!!.first()
            assertTrue { firtsElement.id == listMock.data!!.first().id }
        }
    }
}
