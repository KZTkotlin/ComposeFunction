package com.example.composefunction.ui.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class HistoryScreenState(
    private val viewModel: HistoryScreenViewModel,
) {
    val uiState: HistoryScreenUiState
        @Composable get() = viewModel.uiState.collectAsStateWithLifecycle().value
}

@Composable
fun rememberHistoryScreenState(
    viewModel: HistoryScreenViewModel = hiltViewModel(),
): HistoryScreenState {
    return remember {
        HistoryScreenState(
            viewModel = viewModel
        )
    }
}
