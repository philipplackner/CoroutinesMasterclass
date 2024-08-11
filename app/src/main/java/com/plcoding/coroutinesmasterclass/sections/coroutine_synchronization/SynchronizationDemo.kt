package com.plcoding.coroutinesmasterclass.sections.coroutine_synchronization

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

fun synchronizationDemo() {
    var count = 0
    val mutex = Mutex()
    GlobalScope.launch {
        (1..100_000).map {
            launch {
                mutex.withLock {
                    count++
                }
            }
        }.joinAll()

        println("The count is $count")
    }
}