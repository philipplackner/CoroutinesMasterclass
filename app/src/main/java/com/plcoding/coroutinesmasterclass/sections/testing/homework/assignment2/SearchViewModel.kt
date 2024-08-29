@file:OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)

package com.plcoding.coroutinesmasterclass.sections.testing.homework.assignment2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext

data class SearchState(
    val query: String = "",
    val validationMessages: String? = null,
    val isLoading: Boolean = false,
    val searchResults: List<String> = emptyList()
)

class SearchViewModel : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val validationMessages = MutableStateFlow<List<String>>(emptyList())
    private val isLoading = MutableStateFlow(false)

    private val searchResults = searchQuery
        .debounce(300L)
        .onEach { query ->
            if (query.isNotEmpty()) {
                isLoading.value = true
            }
        }
        .flatMapLatest { query ->
            if (isInputValid(query)) {
                flowOf(searchDatabase(query))
            } else {
                emptyFlow()
            }
        }
        .onEach {
            isLoading.value = false
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    var state = combine(
        searchQuery,
        validationMessages,
        isLoading,
        searchResults
    ) { query, validationMessages, isLoading, searchResults ->
        val validationMessage = if (validationMessages.isNotEmpty()) {
            validationMessages.joinToString("\n")
        } else null
        SearchState(
            query = query,
            validationMessages = validationMessage,
            isLoading = isLoading,
            searchResults = searchResults
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SearchState())

    private suspend fun isInputValid(input: String): Boolean {
        val messages = mutableListOf<String>()
        if (input.isBlank()) {
            messages.add("Input cannot be empty.")
        }
        if (input.any { it.isDigit() }) {
            messages.add("Input cannot contain a digit")
        }
        validationMessages.value = messages
        return messages.isEmpty()
    }

    private suspend fun searchDatabase(query: String): List<String> {
        return withContext(Dispatchers.IO) {
            val lowercaseQuery = query.lowercase()
            val searchJobA = async {
                delay(450)
                petNamesSourceA
                    .filter { name ->
                        name
                            .lowercase()
                            .contains(lowercaseQuery)
                    }
            }
            val searchJobB = async {
                delay(600)
                petNamesSourceB
                    .filter { name ->
                        name
                            .lowercase()
                            .contains(lowercaseQuery)
                    }
            }

            val resultsA = searchJobA.await()
            val resultsB = searchJobB.await()

            (resultsA + resultsB).distinct()
                .also { println("results: $it") }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery.value = newQuery
    }
}