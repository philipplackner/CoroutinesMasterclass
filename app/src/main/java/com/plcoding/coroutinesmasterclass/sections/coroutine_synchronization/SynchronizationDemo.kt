@file:OptIn(ExperimentalCoroutinesApi::class)

package com.plcoding.coroutinesmasterclass.sections.coroutine_synchronization

import android.graphics.Bitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.roundToInt
import kotlin.random.Random

fun synchronizationDemo() {
    val normalHashMap = hashMapOf<Int, Int>()
    val concurrentHashMap = ConcurrentHashMap<Int, Int>()

    GlobalScope.launch(Dispatchers.IO.limitedParallelism(1)) {
        (1..100000).map {
            launch {
                val random = Random.nextInt(1, 9)

                val concurrentCount = concurrentHashMap[random] ?: 0
                concurrentHashMap[random] = concurrentCount + 1

                val normalCount = normalHashMap[random] ?: 0
                normalHashMap[random] = normalCount + 1
            }
        }.joinAll()

        println("Normal hashmap:")
        normalHashMap
            .toSortedMap()
            .forEach { (key, count) ->
                println("$key: $count")
            }

        println("Concurrent hashmap:")
        concurrentHashMap
            .toSortedMap()
            .forEach { (key, count) ->
                println("$key: $count")
            }
    }
}

class MySynchronizedClass(
    private val coroutineScope: CoroutineScope =
        CoroutineScope(Dispatchers.IO.limitedParallelism(1) + SupervisorJob())
) {

    fun doSomething() {
        coroutineScope.launch {

        }
    }
}