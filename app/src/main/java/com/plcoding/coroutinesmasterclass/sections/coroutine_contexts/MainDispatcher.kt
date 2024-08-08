package com.plcoding.coroutinesmasterclass.sections.coroutine_contexts

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

suspend fun mainDispatcher() {
    withContext(Dispatchers.Main) {
        delay(2000L)

        withContext(Dispatchers.Main.immediate) {

        }
    }
}