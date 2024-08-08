@file:OptIn(DelicateCoroutinesApi::class)

package com.plcoding.coroutinesmasterclass.sections.coroutine_contexts

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

fun ioDefaultDispatcher() {
    val threads = hashMapOf<Long, String>()
    val job = GlobalScope.launch(Dispatchers.Default) {
        repeat(100) {
            launch {
                threads[Thread.currentThread().id] = Thread.currentThread().name
                // Equivalent of a blocking network call
//                Thread.sleep(1000L)

                (1..100_000L).map {
                    it * it
                }
            }
        }
    }

    GlobalScope.launch {
        val timeMillis = measureTimeMillis {
            job.join()
        }
        println("Launched ${threads.keys.size} threads in $timeMillis ms.")
    }
}