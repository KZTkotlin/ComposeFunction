package com.example.composefunction.ui.textedit

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.composefunction.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TextEditScreenViewModel @Inject constructor(
    // TODO : Repo or UseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<TextEditScreenUiState> =
        MutableStateFlow(TextEditScreenUiState())
    val uiState: StateFlow<TextEditScreenUiState> = _uiState.asStateFlow()

    fun checkUser(user: String) {
        _uiState.update {
            it.copy(
                isValidateMessage = true,
            )
        }
        val pattern = if (Patterns.EMAIL_ADDRESS != null) {
            Patterns.EMAIL_ADDRESS
        } else {
            null
        }
        pattern?.let {
            // 入力文字がメールアドレスの形式かチェックする
            val patternMatch = pattern.matcher(user).matches()
            if (patternMatch) {
                _uiState.update {
                    it.copy(
                        checkUserMessageId = R.string.text_edit_check_user_validate_ok
                    )
                }
                return
            }
        }
        _uiState.update {
            it.copy(
                checkUserMessageId = R.string.text_edit_check_user_validate_error
            )
        }
    }
}
