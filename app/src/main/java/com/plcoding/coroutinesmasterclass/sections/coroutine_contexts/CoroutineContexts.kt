@file:OptIn(ExperimentalStdlibApi::class)

package com.plcoding.coroutinesmasterclass.sections.coroutine_contexts

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

suspend fun queryDatabase() {
    val job = coroutineContext[Job]
    val name = coroutineContext[CoroutineName]
    val handler = coroutineContext[CoroutineExceptionHandler]
    val dispatcher = coroutineContext[CoroutineDispatcher]

    CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
        println("Dispatcher: ${coroutineContext[CoroutineDispatcher]}")
        println("Name: ${coroutineContext[CoroutineName]}")
    }
//
//    println("Job: $job")
//    println("Name: $name")
//    println("Handler: $handler")
//    println("Dispatcher: $dispatcher")
}