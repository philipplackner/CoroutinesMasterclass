package com.plcoding.coroutinesmasterclass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.plcoding.coroutinesmasterclass.homework.AssignmentTwoScreen
import com.plcoding.coroutinesmasterclass.ui.theme.CoroutinesMasterclassTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CoroutinesMasterclassTheme {
                AssignmentTwoScreen()
            }
        }
    }
}

