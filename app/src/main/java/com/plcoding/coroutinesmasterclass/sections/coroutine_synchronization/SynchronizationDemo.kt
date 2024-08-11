package com.plcoding.coroutinesmasterclass.sections.coroutine_synchronization

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

fun synchronizationDemo() {
    var count = 0
    GlobalScope.launch {
        (1..100_000).map {
            launch {
                count++
            }
        }.joinAll()

        println("The count is $count")
    }
}