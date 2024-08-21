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

        val job = lifecycleScope.launch {
//            val innerJob1 = launch {
//                delay(2000L)
//                println("Inner job 1 finished")
//            }
//            val innerJob2 = launch {
//                delay(1000L)
//                println("Inner job 2 finished")
//            }

            val profileDeferred = async {
                println("Fetching profile data...")
                delay(2000L)
                "profile"
            }
            val postsDeferred = async {
                println("Fetching profile posts...")
                delay(3000L)
                "posts"
            }

            val timeMillis = measureTimeMillis {
                val posts = postsDeferred.await()
                val profile = profileDeferred.await()

                println("Profile loaded: $profile, $posts")
            }
            println("Jobs took $timeMillis milliseconds.")
        }

        setContent {
            CoroutinesMasterclassTheme {
            }
        }
    }
}