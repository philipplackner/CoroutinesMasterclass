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
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.coroutinesmasterclass.sections.flows_in_practice.backpressure.backpressureDemo
import com.plcoding.coroutinesmasterclass.sections.flows_in_practice.form_ui.FormUi
import com.plcoding.coroutinesmasterclass.sections.flows_in_practice.location_tracking.locationTracking
import com.plcoding.coroutinesmasterclass.sections.flows_in_practice.task.flatMapDemo
import com.plcoding.coroutinesmasterclass.sections.flows_in_practice.timer.TimerUi
import com.plcoding.coroutinesmasterclass.sections.flows_in_practice.websocket.WebSocketUi
import com.plcoding.coroutinesmasterclass.sections.testing.homework.assignment2.SearchScreen
import com.plcoding.coroutinesmasterclass.sections.testing.homework.assignment2.SearchViewModel
import com.plcoding.coroutinesmasterclass.ui.theme.CoroutinesMasterclassTheme
import com.plcoding.coroutinesmasterclass.util.db.TaskDao
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            CoroutinesMasterclassTheme {
                val viewModel: SearchViewModel = viewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                SearchScreen(
                    state = state,
                    onSearchTextChange = viewModel::onSearchQueryChange
                )
            }
        }
    }
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
        if(hasFineLocationPermission && hasCoarseLocationPermission) {
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