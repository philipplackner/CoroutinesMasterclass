package com.plcoding.coroutinesmasterclass.sections.coroutine_scopes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    fun doSomething() {
        viewModelScope.launch {

        }
    }
}