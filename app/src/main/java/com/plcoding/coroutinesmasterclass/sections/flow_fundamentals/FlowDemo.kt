package com.plcoding.coroutinesmasterclass.sections.flow_fundamentals

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun flowDemo() {
    val flow = flow<Int> {
        println("Collection started!")
        delay(1000L)
        emit(1)
        delay(2000L)
        emit(2)
        delay(3000L)
        emit(3)
    }

    flow.onEach {
        println("Collector 1: $it")
    }

    GlobalScope.launch {
        delay(5000L)
        flow.onEach {
            println("Collector 2: $it")
        }
    }
}