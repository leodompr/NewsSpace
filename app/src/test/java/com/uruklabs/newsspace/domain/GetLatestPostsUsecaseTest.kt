package com.uruklabs.newsspace.domain

import android.util.Log
import com.uruklabs.newsspace.configureTestAppComponent
import com.uruklabs.newsspace.core.Query
import com.uruklabs.newsspace.core.Resouce
import com.uruklabs.newsspace.data.SpaceFlightNewsCategory
import com.uruklabs.newsspace.data.entites.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertFalse

import kotlin.test.assertNotNull
import kotlin.test.assertTrue


class GetLatestPostsUsecaseTest : KoinTest {

    val getLatestPostsUsecase: GetLatestPostsUseCase by inject()

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
    fun deve_RetornarResultadoNaoNulo_AoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsUsecase(Query(type = SpaceFlightNewsCategory.ARTICLES.value))

            assertNotNull(result)
        }
    }

    @Test
    fun deve_RetornarObjetoDoTipoCorreto_AoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsUsecase(Query(type = SpaceFlightNewsCategory.ARTICLES.value))

            assertTrue(result is Flow<Resouce<List<Post>>>)
        }
    }

    @Test
    fun deve_RetornarResultadoNaoVazio_AoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsUsecase(Query(type = SpaceFlightNewsCategory.ARTICLES.value))
            Log.d("resultttt", result.first().toString())
            result.first().data?.isEmpty()?.let { assertFalse(it) }
        }
    }


}