package com.example.composefunction.ui.contents

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ContentsScreenViewModel @Inject constructor(
    // TODO : Repo or UseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<ContentsScreenUiState> =
        MutableStateFlow(ContentsScreenUiState())
    val uiState: StateFlow<ContentsScreenUiState> = _uiState.asStateFlow()
}
