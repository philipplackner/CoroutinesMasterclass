package com.plcoding.coroutinesmasterclass.sections.testing

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalStdlibApi::class)
class FibonacciKtTest {

    @Test
    fun testFibonacci() = runTest {
        val dispatcher = coroutineContext[CoroutineDispatcher]
        val result = fib(30, dispatcher!!)

        assertThat(result).isEqualTo(832_040)
    }
}