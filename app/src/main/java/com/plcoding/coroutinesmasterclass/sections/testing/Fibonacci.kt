package com.plcoding.coroutinesmasterclass.sections.testing

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// fib(0) = 1
// fib(1) = 1
// fib(2) = fib(1) + fib(0) = 1 + 1 = 2
// fib(3) = fib(2) + fib(1) = 2 + 1 = 3
suspend fun fib(n: Int, dispatcher: CoroutineDispatcher): Int {
    delay(5000L)
    return withContext(dispatcher) {
        if (n <= 1) {
            n
        } else {
            fib(n-1, dispatcher) + fib(n-2, dispatcher)
        }
    }
}