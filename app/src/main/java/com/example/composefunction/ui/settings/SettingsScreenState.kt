package com.example.composefunction.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class SettingsScreenState(
    private val viewModel: SettingsScreenViewModel
) {
    val uiState: SettingsScreenUiState
        @Composable get() = viewModel.uiState.collectAsStateWithLifecycle().value

}

@Composable
fun rememberSettingsScreenState(
    viewModel: SettingsScreenViewModel = hiltViewModel(),
): SettingsScreenState {
    return remember {
        SettingsScreenState(
            viewModel = viewModel
        )
    }
}
