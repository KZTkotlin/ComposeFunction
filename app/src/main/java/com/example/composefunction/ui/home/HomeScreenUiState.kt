package com.example.composefunction.ui.home


data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isHiddenMessage: Boolean = false,
)
