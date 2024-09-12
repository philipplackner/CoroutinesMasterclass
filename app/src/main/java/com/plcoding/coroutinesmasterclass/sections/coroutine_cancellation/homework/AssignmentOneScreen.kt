package com.plcoding.coroutinesmasterclass.sections.coroutine_cancellation.homework

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plcoding.coroutinesmasterclass.util.RemoteService
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

@Composable
fun AssignmentOneScreen() {
    var isUploading by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isUploading) {
            CircularProgressIndicator()
            Button(onClick = {
                scope.coroutineContext.cancelChildren()
                isUploading = false
            }) {
                Text(text = "Cancel Upload")
            }
        } else {
            Button(onClick = {
                scope.launch {
                    isUploading = true
                    RemoteService.uploadFile()
                    isUploading = false
                }
            }) {
                Text(text = "Upload File")
            }
        }
    }
}