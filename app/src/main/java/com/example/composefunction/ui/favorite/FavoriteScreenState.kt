package com.example.composefunction.ui.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class FavoriteScreenState(
    private val viewModel: FavoriteScreenViewModel
) {
    val uiState: FavoriteScreenUiState
        @Composable get() = viewModel.uiState.collectAsStateWithLifecycle().value

}

@Composable
fun rememberFavoriteScreenState(
    viewModel: FavoriteScreenViewModel = hiltViewModel()
): FavoriteScreenState {
    return remember {
        FavoriteScreenState(
            viewModel = viewModel
        )
    }
}
