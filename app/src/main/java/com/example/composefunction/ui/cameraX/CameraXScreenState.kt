package com.example.composefunction.ui.cameraX

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class CameraXScreenState(
    private val viewModel: CameraXScreenViewModel,
) {
    val uiState: CameraXScreenUiState
        @Composable get() = viewModel.uiState.collectAsStateWithLifecycle().value

    fun updateImageFilePath(imageFilePath: String){
        viewModel.updateImageFilePath(imageFilePath)
    }
}

@Composable
fun rememberCameraXScreenState(
    viewModel: CameraXScreenViewModel = hiltViewModel(),
): CameraXScreenState {
    return remember {
        CameraXScreenState(
            viewModel = viewModel
        )
    }
}
