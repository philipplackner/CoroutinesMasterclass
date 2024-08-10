package com.plcoding.coroutinesmasterclass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.plcoding.coroutinesmasterclass.ui.theme.CoroutinesMasterclassTheme
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.printStackTrace()
        }
        lifecycleScope
        val coroutineScope = CoroutineScope(
            Dispatchers.Main.immediate + SupervisorJob()
        )
        coroutineScope.launch(handler) {
            launch {
                delay(1000L)
                throw Exception("Oops!")
            }
            delay(2000L)
            println("Coroutine1 finished!")
        }
        coroutineScope.launch(handler) {
            delay(2000L)
            println("Coroutine2 finished!")
        }

        setContent {
            CoroutinesMasterclassTheme {}
        }
    }
}