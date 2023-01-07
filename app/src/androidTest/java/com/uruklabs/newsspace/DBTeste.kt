package com.uruklabs.newsspace

import com.uruklabs.newsspace.data.entites.database.LaunchDB
import com.uruklabs.newsspace.data.entites.database.PostDB
import org.junit.Before

open class DBTeste {
    lateinit var listDbPosts: List<PostDB>

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
}