package com.plcoding.coroutinesmasterclass.sections.flow_fundamentals

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun flowDemo() {
    GlobalScope.launch {
        flow<Int> {
            delay(1000L)
            emit(1)
            delay(2000L)
            emit(2)
            delay(3000L)
            emit(3)
        }.collect {
            println("Current value: $it")
        }
    }
}