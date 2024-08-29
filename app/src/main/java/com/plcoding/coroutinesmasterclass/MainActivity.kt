package com.plcoding.coroutinesmasterclass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.coroutinesmasterclass.sections.coroutine_cancellation.homework.assignmenttwo.MoneyTransferViewModel
import com.plcoding.coroutinesmasterclass.sections.coroutine_cancellation.homework.assignmenttwo.MoneyTransferScreen
import com.plcoding.coroutinesmasterclass.ui.theme.CoroutinesMasterclassTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutinesMasterclassTheme {
                val viewModel: MoneyTransferViewModel = viewModel()

                MoneyTransferScreen(
                    state = viewModel.state,
                    onAction = viewModel::onAction
                )
            }
        }
    }
}