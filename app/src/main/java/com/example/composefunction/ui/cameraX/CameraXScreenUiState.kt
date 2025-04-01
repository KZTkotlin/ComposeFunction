package com.example.composefunction.ui.cameraX

import com.example.composefunction.constants.Constants

data class CameraXScreenUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val imageFilePath: String = Constants.INIT_STRING_VALUE,
)

enum class PermissionCameraState {
    Checking,
    Granted,
    Denied,
}
