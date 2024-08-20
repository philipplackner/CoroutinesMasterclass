package com.plcoding.coroutinesmasterclass.sections.flows_in_practice.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.coroutinesmasterclass.util.db.TaskDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneOffset

class TaskViewModel(
    private val taskDao: TaskDao
): ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    val tasks = selectedDate
        .map { date ->
            val startMillis = date
                .atStartOfDay()
                .toEpochSecond(ZoneOffset.UTC) * 1000L
            val endMillis = date
                .plusDays(1)
                .atStartOfDay()
                .toEpochSecond(ZoneOffset.UTC) * 1000L

            startMillis to endMillis
        }
        .flatMapLatest { (startMillis, endMillis) ->
            taskDao.getTasksBetween(startMillis, endMillis)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

}