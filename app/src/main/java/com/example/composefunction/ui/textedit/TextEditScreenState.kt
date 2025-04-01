package com.example.composefunction.ui.textedit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class TextEditScreenState(
    private val viewModel: TextEditScreenViewModel,
) {
    val uiState: TextEditScreenUiState
        @Composable get() = viewModel.uiState.collectAsStateWithLifecycle().value

    fun checkUser(user: String) {
        viewModel.checkUser(user)
    }
}

@Composable
fun rememberTextEditScreenState(
    viewModel: TextEditScreenViewModel = hiltViewModel(),
): TextEditScreenState {
    return remember {
        TextEditScreenState(
            viewModel = viewModel
        )
    }
}
