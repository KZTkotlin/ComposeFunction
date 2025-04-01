package com.example.composefunction.ui.cameraX

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CameraXScreenViewModel @Inject constructor(
    // TODO : Repo or UseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<CameraXScreenUiState>
        = MutableStateFlow(CameraXScreenUiState())
    val uiState: StateFlow<CameraXScreenUiState> = _uiState.asStateFlow()

    fun updateImageFilePath(imageFilePath: String) {
        _uiState.update {
            it.copy(
                imageFilePath = imageFilePath
            )
        }
    }
}
