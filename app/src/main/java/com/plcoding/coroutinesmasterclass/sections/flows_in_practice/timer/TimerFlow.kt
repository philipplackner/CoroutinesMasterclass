package com.plcoding.coroutinesmasterclass.sections.flows_in_practice.timer

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.roundToLong
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

fun timeAndEmit(emissionsPerSecond: Float): Flow<Duration> {
    return flow {
        var lastEmitTime = System.currentTimeMillis()
        emit(Duration.ZERO)

        while (true) {
            delay((1000L / emissionsPerSecond).roundToLong())

            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - lastEmitTime

            emit(elapsedTime.milliseconds)
            lastEmitTime = currentTime
        }
    }
}