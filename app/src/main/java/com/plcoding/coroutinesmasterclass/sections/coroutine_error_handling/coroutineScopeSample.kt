package com.plcoding.coroutinesmasterclass.sections.coroutine_error_handling

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

data class Profile(
    val username: String,
    val followerCount: Int,
    val posts: List<String>
)

suspend fun getProfile(api: HttpClient, userId: String) {
    coroutineScope {
        val profileData = async {
            api.get(urlString = "https://pl-coding.com/profile/$userId")
        }
        val profilePosts = async {
            api.get(urlString = "https://pl-coding.com/posts/$userId")
        }

        val profileResponse = profileData.await()
        val postsResponse = profilePosts.await()
//        Profile(
//            username = TODO(),
//            followerCount = TODO(),
//            posts = TODO()
//        )
    }
}