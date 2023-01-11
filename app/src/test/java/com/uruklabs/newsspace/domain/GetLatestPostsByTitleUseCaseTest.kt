package com.uruklabs.newsspace.domain

import com.uruklabs.newsspace.core.Query
import com.uruklabs.newsspace.core.Resouce
import com.uruklabs.newsspace.data.SpaceFlightNewsCategory
import com.uruklabs.newsspace.data.entites.model.Post
import com.uruklabs.newsspace.data.repository.PostRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetLatestPostsByTitleUseCaseTest {

    private lateinit var getLatestPostsByTitleUseCase: GetLatestPostsByTitleUseCase
    private val repository: PostRepository = mockk()
    private val type = SpaceFlightNewsCategory.ARTICLES.value
    private val query = "moon"
    private val queryData = Query(type = type, query = query)
    private val listMock: Resouce<List<Post>> = Resouce.Success(
        data = listOf(
            Post(
                id = 12782,
                title = "Moon-3 mission cleared for launch",
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

    // Antes de rodas o ambiente de testes iniciamos a aplicação do Koin
    @Before
    fun setup() {
        getLatestPostsByTitleUseCase = GetLatestPostsByTitleUseCase(repository)
        every { repository.getlistPostsByTitle(category = type, query = query) }.returns(
            flowOf(listMock)
        )
    }

    @Test
    fun deve_RetornarResultadosValidos_AoExecutarBusca() {
        runBlocking {
            val result = getLatestPostsByTitleUseCase(queryData)
            var assertion = true
            result.first().data?.forEach {
                assertion = assertion && it.title.lowercase().contains(query)
            }
            assertTrue(assertion)
        }
    }
}
