package com.plcoding.coroutinesmasterclass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.plcoding.coroutinesmasterclass.sections.coroutine_basics.fetchData
import com.plcoding.coroutinesmasterclass.ui.theme.CoroutinesMasterclassTheme
import com.plcoding.coroutinesmasterclass.util.RotatingBoxScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        val outerJob = lifecycleScope.launch {
//            val innerJob = launch {
//                launch {
//                    delay(3000L)
//                }
//                launch {
//                    delay(2000L)
//                }
//                delay(1000L)
//            }
//            val timeMillis = measureTimeMillis {
//                innerJob.join()
//            }
//            println("Inner job took $timeMillis ms.")
//        }

        lifecycleScope.launch {
            val profileJson = async {
                getProfile()
            }
            val postsJson = async {
                getPosts()
            }

            val timeMillis = measureTimeMillis {
                println("Profile: ${profileJson.await()}, posts: ${postsJson.await()}")
            }
            println("Fetching data took $timeMillis ms.")
        }

        setContent {
            CoroutinesMasterclassTheme {
            }
        }
    }
}

suspend fun getProfile(): String {
    delay(1000L)
    return "{profile: {}}"
}

suspend fun getPosts(): String {
    delay(1500L)
    return "{posts: []}"
}