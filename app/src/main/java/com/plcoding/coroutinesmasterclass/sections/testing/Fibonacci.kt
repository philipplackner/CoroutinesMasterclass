package com.plcoding.coroutinesmasterclass.sections.testing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// fib(0) = 1
// fib(1) = 1
// fib(2) = fib(1) + fib(0) = 1 + 1 = 2
// fib(3) = fib(2) + fib(1) = 2 + 1 = 3
suspend fun fib(n: Int): Int {
    return withContext(Dispatchers.Default) {
        if (n <= 1) {
            n
        } else {
            fib(n-1) + fib(n-2)
        }
    }
}