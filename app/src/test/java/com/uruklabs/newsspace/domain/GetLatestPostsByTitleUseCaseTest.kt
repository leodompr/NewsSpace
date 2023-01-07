package com.uruklabs.newsspace.domain

import com.uruklabs.newsspace.configureTestAppComponent
import com.uruklabs.newsspace.data.SpaceFlightNewsCategory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertTrue


class GetLatestPostsByTitleUseCaseTest : KoinTest {

    val getLatestPostsByTitleUseCase: GetLatestPostsByTitleUseCase by inject()
    private val type = SpaceFlightNewsCategory.ARTICLES.value
    private val query = "moon"

    companion object {

        //Antes de rodas o ambiente de testes iniciamos a aplicação do Koin
        @BeforeClass
        @JvmStatic
        fun setup() {
            configureTestAppComponent()
        }

        //Após, desligar a aplicação do Koin
        @AfterClass
        fun tearDown() {
            stopKoin()
        }

    }

    @Test
    fun deve_RetornarResultadosValidos_AoExecutarBusca() {
        runBlocking {
            val result = getLatestPostsByTitleUseCase(arrayOf(type, query))
            var assertion = true
            result.first().forEach {
                assertion = assertion && it.title.lowercase().contains(query)
            }
            assertTrue(assertion)
        }
    }

}