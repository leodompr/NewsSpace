package com.uruklabs.newsspace.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.uruklabs.newsspace.core.Query
import com.uruklabs.newsspace.core.Resouce
import com.uruklabs.newsspace.core.State
import com.uruklabs.newsspace.data.SpaceFlightNewsCategory
import com.uruklabs.newsspace.data.entites.model.Post
import com.uruklabs.newsspace.domain.GetLatestPostsByTitleUseCase
import com.uruklabs.newsspace.domain.GetLatestPostsUseCase
import com.uruklabs.newsspace.presentation.ui.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.lang.Thread.sleep
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val getLatestPostsUseCase: GetLatestPostsUseCase = mockk(relaxed = true)
    private val getLatestPostsByTitleUseCase: GetLatestPostsByTitleUseCase = mockk(relaxed = true)
    private lateinit var homeViewModel: HomeViewModel
    private val query = Query(type = SpaceFlightNewsCategory.ARTICLES.value)
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

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getLatestPostsUseCase(query) }.returns(flowOf(listMock))
        coEvery { getLatestPostsByTitleUseCase(any()) }.returns(flowOf(listMock))
        homeViewModel = HomeViewModel(getLatestPostsUseCase, getLatestPostsByTitleUseCase)
    }

    @Test
    fun `quando fethLatest é chamado, getLatestPostsUseCase é chamado`() {
        homeViewModel.fethLatest(SpaceFlightNewsCategory.ARTICLES)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { getLatestPostsUseCase(query) }
    }

    @Test
    fun `quando fethLatest é chamado, getLatestPostsUseCase é chamado e retorna uma lista`() {
        homeViewModel.fethLatest(SpaceFlightNewsCategory.ARTICLES)
        testDispatcher.scheduler.advanceUntilIdle()
        val stateSucess = State.Success(listMock.data!!)
        assertEquals(stateSucess, homeViewModel.listPost.value)
    }

    @Test
    fun `quando fethLatest é chamado progressBar é true`(){
        homeViewModel.fethLatest(SpaceFlightNewsCategory.ARTICLES)
        assertEquals(true, homeViewModel.progressBarVisible.value)
    }

    @Test
    fun `quando fetchLateste é finalizado progressBar é false`(){
        homeViewModel.fethLatest(SpaceFlightNewsCategory.ARTICLES)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(false, homeViewModel.progressBarVisible.value)
    }


    @Test
    fun `quando searchPostsByTile é chamado, getLatestPostsByTitleUseCase é chamado`() {
        val query = Query(type = SpaceFlightNewsCategory.ARTICLES.value, query = "title")
        homeViewModel.searchPostsByTile("title")
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { getLatestPostsByTitleUseCase(query) }
    }

    @Test
    fun `quando searchPostsByTile é chamado, getLatestPostsByTitleUseCase é chamado e retorna uma lista`() {
        homeViewModel.searchPostsByTile("title")
        testDispatcher.scheduler.advanceUntilIdle()
        val stateSucess = State.Success(listMock.data!!)
        assertEquals(stateSucess, homeViewModel.listPost.value)
    }

    @Test
    fun `quando searchPostsByTile é chamado progressBar é true`(){
        homeViewModel.searchPostsByTile("title")
        assertEquals(true, homeViewModel.progressBarVisible.value)
    }

    @Test
    fun `quando searchPostsByTile é finalizado progressBar é false`(){
        homeViewModel.searchPostsByTile("title")
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(false, homeViewModel.progressBarVisible.value)
    }

}