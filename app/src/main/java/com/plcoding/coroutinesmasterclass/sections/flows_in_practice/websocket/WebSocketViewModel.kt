package com.plcoding.coroutinesmasterclass.sections.flows_in_practice.websocket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.coroutinesmasterclass.util.api.HttpClientFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class WebSocketLog(
    val formattedTime: String,
    val log: String
)

class WebSocketViewModel: ViewModel() {

    private val client = WebSocketClient(HttpClientFactory.create())

    val receivedLogs = client
        .listenToSocket("wss://echo.websocket.org/")
        .runningFold(initial = emptyList<WebSocketLog>()) { logs, newLog ->
            val formattedTime = DateTimeFormatter
                .ofPattern("dd-MM-yyyy, hh:mm:ss")
                .format(LocalDateTime.now())

            logs + WebSocketLog(
                formattedTime = formattedTime,
                log = newLog
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    fun sendMessage(text: String) {
        viewModelScope.launch {
            client.sendMessage(text)
        }
    }
}