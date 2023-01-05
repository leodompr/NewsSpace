package com.uruklabs.newsspace

import com.uruklabs.newsspace.data.model.Post
import com.uruklabs.newsspace.domain.GetLatestPostsUsecase
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

    val getLatestPostsUsecase: GetLatestPostsUsecase by inject()

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
            val result = getLatestPostsUsecase()

            assertNotNull(result)
        }
    }

    @Test
    fun deve_RetornarObjetoDoTipoCorreto_AoConectarComRepositorio(){
        runBlocking {
            val result = getLatestPostsUsecase()

            assertTrue(result is Flow<List<Post>>)
        }
    }

    @Test
    fun deve_RetornarResultadoNaoVazio_AoConectarComRepositorio(){
        runBlocking {
            val result = getLatestPostsUsecase()

            assertFalse(result.first().isEmpty())
        }
    }


}