@file:OptIn(ExperimentalFoundationApi::class)

package com.plcoding.coroutinesmasterclass.sections.testing.homework.assignment2

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(
    state: SearchState,
    onSearchTextChange: (String) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = state.query,
                onValueChange = { onSearchTextChange(it) }
            )
            if (state.validationMessages != null) {
                Text(text = "${state.validationMessages}")
            }
            Box(
                modifier = Modifier.weight(1f),
            ) {
                if(state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        state.searchResults,
                        key = { it }
                    ) { item ->
                        Text(text = item, modifier = Modifier.animateItemPlacement())
                    }
                }
            }
        }
    }
}