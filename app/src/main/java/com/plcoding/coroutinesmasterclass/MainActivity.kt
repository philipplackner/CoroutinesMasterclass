package com.plcoding.coroutinesmasterclass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plcoding.coroutinesmasterclass.sections.coroutine_cancellation.homework.AssignmentOneScreen
import com.plcoding.coroutinesmasterclass.ui.theme.CoroutinesMasterclassTheme
import com.plcoding.coroutinesmasterclass.util.RemoteService.uploadFile
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutinesMasterclassTheme {
                AssignmentOneScreen()
            }
        }
    }
}