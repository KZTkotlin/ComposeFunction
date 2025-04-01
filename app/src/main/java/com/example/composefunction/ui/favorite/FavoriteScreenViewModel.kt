package com.example.composefunction.ui.favorite

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    // TODO : Repo or UseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<FavoriteScreenUiState> =
        MutableStateFlow(FavoriteScreenUiState())
    val uiState: StateFlow<FavoriteScreenUiState> = _uiState.asStateFlow()

}
