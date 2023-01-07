package com.uruklabs.newsspace

import com.uruklabs.newsspace.data.entites.model.Post
import com.uruklabs.newsspace.data.entites.network.LaunchDTO
import com.uruklabs.newsspace.data.entites.network.PostDTO
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class PostDTOTest {

    val launchDTO = LaunchDTO(
        id = "0d779392-1a36-4c1e-b0b8-ec11e3031ee6",
        provider = "Launch Library 2"
    )

    val postDTO = PostDTO(
        id = 12782,
        title = "Crew-3 mission cleared for launch",
        url = "https://spacenews.com/crew-3-mission-cleared-for-launch/",
        imageUrl = "https://spacenews.com/wp-content/uploads/2021/11/crew2-chutes.jpg",
        summary = "NASA and SpaceX are ready to proceed with the launch of a commercial crew mission Nov. 10 after overcoming weather and astronaut health issues as well as concerns about the spacecraft’s parachutes.",
        publishedAt = "2021-11-10T09:27:02.000Z",
        updatedAt = "2021-11-10T09:38:23.654Z",
        launches = arrayOf(launchDTO)
    )

    @Test
    fun `deve converter corretamente DTO em entidade de modelo`(){
        val post : Post = postDTO.toModel()
        assertTrue(post is Post)
        assertTrue(post.title == postDTO.title)
        assertTrue(post.launches.isNotEmpty())
    }


}