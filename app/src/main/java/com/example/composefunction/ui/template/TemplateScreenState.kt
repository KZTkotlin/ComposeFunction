package com.example.composefunction.ui.template

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class TemplateScreenState(
    private val viewModel: TemplateScreenViewModel,
) {
    val uiState: TemplateScreenUiState
        @Composable get() = viewModel.uiState.collectAsStateWithLifecycle().value
}

@Composable
fun rememberTemplateScreenState(
    viewModel: TemplateScreenViewModel = hiltViewModel(),
): TemplateScreenState {
    return remember {
        TemplateScreenState(
            viewModel = viewModel
        )
    }
}
