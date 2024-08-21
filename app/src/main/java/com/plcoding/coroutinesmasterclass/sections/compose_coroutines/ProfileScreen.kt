package com.plcoding.coroutinesmasterclass.sections.compose_coroutines

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val snackbarState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    var isShowingSnackbar by remember {
        mutableStateOf(false)
    }
    Scaffold(
       snackbarHost = {
           SnackbarHost(hostState = snackbarState)
       }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                scope.launch {
                    isShowingSnackbar = true
                    snackbarState.showSnackbar(
                        message = "Hello world!"
                    )
                    isShowingSnackbar = false
                }
            }) {
                Text(
                    text = if(isShowingSnackbar) {
                        "Snackbar showing!"
                    } else {
                        "Snackbar not showing!"
                    }
                )
            }
        }
    }
}