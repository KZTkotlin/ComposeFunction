package com.example.composefunction.ui.template

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TemplateScreenViewModel @Inject constructor(
    // TODO : Repo or UseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<TemplateScreenUiState>
        = MutableStateFlow(TemplateScreenUiState())
    val uiState: StateFlow<TemplateScreenUiState> = _uiState.asStateFlow()
}
