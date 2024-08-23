package com.plcoding.coroutinesmasterclass.sections.flows_in_practice.websocket

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WebSocketClient(
    private val httpClient: HttpClient
) {
    private var session: WebSocketSession? = null

    suspend fun sendMessage(text: String) {
        session?.send(text)
    }

    fun listenToSocket(url: String): Flow<String> {
        return callbackFlow {
            println("Attempting to connect to socket...")
            session = httpClient.webSocketSession(
                urlString = url
            )

            session?.let { session ->
                session
                    .incoming
                    .consumeAsFlow()
                    .filterIsInstance<Frame.Text>()
                    .collect {
                        send(it.readText())
                    }
            } ?: run {
                session?.close()
                session = null
                close()
            }

            awaitClose {
                launch(NonCancellable) {
                    session?.close()
                    session = null
                }
            }
        }
    }
}