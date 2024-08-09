package com.plcoding.coroutinesmasterclass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.plcoding.coroutinesmasterclass.sections.coroutine_cancellation.pollingTask
import com.plcoding.coroutinesmasterclass.sections.coroutine_cancellation.trap3.FileManager
import com.plcoding.coroutinesmasterclass.sections.coroutine_contexts.main_safety.BitmapCompressor
import com.plcoding.coroutinesmasterclass.sections.coroutine_contexts.main_safety.PhotoPickerScreen
import com.plcoding.coroutinesmasterclass.ui.theme.CoroutinesMasterclassTheme
import com.plcoding.coroutinesmasterclass.util.api.HttpClientFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val fileManager = FileManager(applicationContext)
        lifecycleScope.launch {
            val job = launch {
                fileManager.writeRecordsToFile()
            }
            delay(3000L)
            job.cancel()
        }

//        val compressor = BitmapCompressor(applicationContext)

        setContent {
            CoroutinesMasterclassTheme {
//                PhotoPickerScreen(compressor = compressor)
            }
        }
    }
}