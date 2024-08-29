package com.plcoding.coroutinesmasterclass

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // USE THIS TO RUN YOUR Leaderboard CLASS
//        val leaderboard = Leaderboard()
//
//
//        leaderboard.addListener { topScores: String ->
//            println("New Top Scores:")
//            println(topScores + "\n\n")
//        }
//
//        GlobalScope.launch {
//            (1..5_000).map { index ->
//                launch {
//                    val playerName = "Player $index"
//                    val playerScore = Random.nextInt(1, 10_0000)
//                    leaderboard.updateScore(playerName, playerScore)
//                }
//            }.joinAll()
//            println("Completed!")
//        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    suspend fun Context.getLocation(): Location {
        return suspendCancellableCoroutine { continuation ->
            val locationManager = getSystemService<LocationManager>()!!

            val hasFineLocationPermission = ActivityCompat.checkSelfPermission(
                this@getLocation,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            val hasCoarseLocationPermission = ActivityCompat.checkSelfPermission(
                this@getLocation,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val signal = CancellationSignal()
            if (hasFineLocationPermission && hasCoarseLocationPermission) {
                locationManager.getCurrentLocation(
                    LocationManager.NETWORK_PROVIDER,
                    signal,
                    mainExecutor
                ) { location ->
                    println("Got location: $location")
                    continuation.resume(location)
                }
            } else {
                continuation.resumeWithException(
                    RuntimeException("Missing location permission")
                )
            }

            continuation.invokeOnCancellation {
                signal.cancel()
            }
        }
    }
}