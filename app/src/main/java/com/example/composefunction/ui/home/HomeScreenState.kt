package com.example.composefunction.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class HomeScreenState(
    private val viewModel: HomeScreenViewModel
) {
    val uiState: HomeScreenUiState
        @Composable get() = viewModel.uiState.collectAsStateWithLifecycle().value

    fun onDisplayHiddenMessage() {
        viewModel.onDisplayHiddenMessage()
    }
}

@Composable
fun rememberHomeScreenState(
    viewModel: HomeScreenViewModel = hiltViewModel()
): HomeScreenState {
    return remember {
        HomeScreenState(
            viewModel = viewModel
        )
    }
}
