@file:OptIn(ExperimentalStdlibApi::class)

package com.plcoding.coroutinesmasterclass.sections.coroutine_contexts

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

suspend fun queryDatabase() {
    val job = coroutineContext[Job]
    println("Job: $job")

    val dispatcher = coroutineContext[CoroutineDispatcher]
    println("Dispatcher: $dispatcher")

    val handler = coroutineContext[CoroutineExceptionHandler]

    val name = coroutineContext[CoroutineName]
    println("Name: $name")

    CoroutineScope(
        Dispatchers.Main.minusKey(CoroutineDispatcher)
    ).launch {
        println("Dispatcher: ${coroutineContext[CoroutineDispatcher]}")
        println("Name: ${coroutineContext[CoroutineName]}")
    }
}