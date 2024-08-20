package com.plcoding.coroutinesmasterclass.sections.flows_in_practice.task

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun flatMapDemo() {
    flow<Int> {
        emit(1)
        delay(500L)
        emit(2)
        delay(500L)
        emit(3)
    }.flatMapLatest {
        flow {
            emit("One")
            delay(1000L)
            emit("Two")
            delay(1000L)
            emit("Three")
        }
    }
        .onEach {
            println("Emission is $it")
        }
        .launchIn(GlobalScope)
}