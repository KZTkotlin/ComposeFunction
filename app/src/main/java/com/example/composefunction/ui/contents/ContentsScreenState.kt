package com.example.composefunction.ui.contents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class ContentsScreenState(
    private val viewModel: ContentsScreenViewModel
) {
    val uiState: ContentsScreenUiState
        @Composable get() = viewModel.uiState.collectAsStateWithLifecycle().value


}

@Composable
fun rememberContentsScreenState(
    viewModel: ContentsScreenViewModel = hiltViewModel()
): ContentsScreenState {
    return remember {
        ContentsScreenState(
            viewModel = viewModel
        )
    }
}
