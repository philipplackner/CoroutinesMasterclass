package com.plcoding.coroutinesmasterclass.sections.coroutine_cancellation

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend fun pollingTask(client: HttpClient) {
    while (true) {
        try {
            println("Polling network resource...")

            val posts = client.get(
                urlString = "https://jsonplaceholder.org/posts"
            )

            println("Received posts!")

            delay(30000L)
        } catch (e: Exception) {
            coroutineContext.ensureActive()

            println("Oops, something went wrong, make sure you're connected" +
                    "to the internet.")
        }
    }
}
