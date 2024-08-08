@file:OptIn(DelicateCoroutinesApi::class)

package com.plcoding.coroutinesmasterclass.sections.coroutine_contexts

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun unconfinedDispatcher() {
    GlobalScope.launch {
        withContext(Dispatchers.Main) {
            println("Thread: ${Thread.currentThread().name}")
            withContext(Dispatchers.Unconfined) {
                println("Thread: ${Thread.currentThread().name}")
                withContext(Dispatchers.IO) {
                    println("Thread: ${Thread.currentThread().name}")
                }
                println("Thread: ${Thread.currentThread().name}")
            }
        }
    }
}