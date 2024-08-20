package com.plcoding.coroutinesmasterclass.sections.flows_in_practice.location_tracking

import android.content.Context
import android.location.Location
import android.provider.Settings.Global
import com.plcoding.coroutinesmasterclass.sections.flow_fundamentals.LocationObserver
import com.plcoding.coroutinesmasterclass.sections.flows_in_practice.timer.timeAndEmit
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.runningReduce
import kotlinx.coroutines.flow.zip
import kotlin.time.Duration
import kotlin.time.DurationUnit

fun Context.locationTracking() {
    val observer = LocationObserver(this)

    timeAndEmit(3f)
        .runningReduce { totalElapsedTime, newElapsedTime ->
            totalElapsedTime + newElapsedTime
        }
        .zip(observer.observeLocation(1000L)) { totalDuration, location ->
            totalDuration to location
        }
        .onEach { (totalDuration, location) ->
            println("Location (${location.latitude}, ${location.longitude}) was tracked " +
                    "after ${totalDuration.inWholeMilliseconds} milliseconds.")
        }
        .runningFold(initial = emptyList<Pair<Duration, Location>>()) { locations, newLocation ->
            locations + newLocation
        }
        .map { allLocations ->
            allLocations.zipWithNext { (duration1, location1), (duration2, location2) ->
                val distance = location1.distanceTo(location2)
                val durationDifference = (duration2 - duration1).toDouble(DurationUnit.HOURS)

                if(durationDifference > 0.0) {
                    ((distance / 1000.0) / durationDifference)
                } else 0.0
            }.average()
        }
        .onEach { avgSpeed ->
            println("Average speed is $avgSpeed km/h")
        }
        .launchIn(GlobalScope)
}