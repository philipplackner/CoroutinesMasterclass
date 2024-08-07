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
import com.plcoding.coroutinesmasterclass.sections.coroutine_basics.fetchData
import com.plcoding.coroutinesmasterclass.ui.theme.CoroutinesMasterclassTheme
import com.plcoding.coroutinesmasterclass.util.RotatingBoxScreen
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        
        setContent {
            CoroutinesMasterclassTheme {
                RotatingBoxScreen()
            }
        }
    }
}