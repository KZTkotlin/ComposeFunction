package com.example.composefunction.ui.textedit

import com.example.composefunction.constants.Constants

data class TextEditScreenUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isValidateMessage: Boolean = false,
    val checkUserMessageId: Int = Constants.INVALID_VALUE,
)
