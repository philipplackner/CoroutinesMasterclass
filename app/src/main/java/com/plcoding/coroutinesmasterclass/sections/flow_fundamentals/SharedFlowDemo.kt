package com.plcoding.coroutinesmasterclass.sections.flow_fundamentals

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun sharedFlowDemo() {
    val sharedFlow = MutableSharedFlow<Int>(
        extraBufferCapacity = 3,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    GlobalScope.launch {
        sharedFlow.onEach {
            println("Collector 1: $it")
            delay(5000L)
        }.launchIn(GlobalScope)

        sharedFlow.onEach {
            println("Collector 2: $it")
        }.launchIn(GlobalScope)
    }

    GlobalScope.launch {
        repeat(10) {
            delay(500L)
            sharedFlow.emit(it)
        }
    }
}