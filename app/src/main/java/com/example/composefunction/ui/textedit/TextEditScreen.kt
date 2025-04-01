package com.example.composefunction.ui.textedit

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composefunction.R
import com.example.composefunction.constants.Constants
import com.example.composefunction.ui.util.CFButton
import com.example.composefunction.ui.util.CFLabelText
import com.example.composefunction.ui.util.CFText
import com.example.composefunction.ui.util.CFTextEdit
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TextEditScreen(
    modifier: Modifier = Modifier,
) {
    val state = rememberTextEditScreenState()

    TextEditScreenBody(
        modifier = modifier,
        uiState = state.uiState,
        checkUser = state::checkUser
    )
}

@Composable
private fun TextEditScreenBody(
    modifier: Modifier = Modifier,
    uiState: TextEditScreenUiState = TextEditScreenUiState(),
    checkUser: (String) -> Unit = {},
) {
    // TODO : パスワード付きとかいろいろなエディタを用意する
    // TODO : 種類ごとにラベルを変える
    // TODO : 入力制御も実装する（正規表現、Pattern）

    val user = remember { mutableStateOf(Constants.INIT_STRING_VALUE) }
    val password = remember { mutableStateOf(Constants.INIT_STRING_VALUE) }
    val isShowPassword = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(0.45f)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            LabelAndTextEdit(
                modifier = Modifier
                    .padding(start = 48.dp, end = 48.dp, bottom = 12.dp),
                labelResourceId = R.string.text_edit_user_label,
                text = user.value,
                onValueChange = {
                    // ここなにやってるかよくわかってない（要解析）
                    // 多分、正規表現じゃない文字列を置換して、
                    // 置換した文字列が最大入力可能文字数以下のときに、文字を入力する
                    val value = it.replace(
                        Constants.INPUT_VALIDATE_REGEX.toRegex(),
                        ""
                    )
                    if (value.length <= Constants.NAME_MAX_LENGTH) {
                        user.value = value
                    }
                },
                placeholderText = stringResource(id = R.string.text_edit_user_place_holder_text),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        // 苦戦したところ、まだ完全解決には至らず
                        // onNextで次のテキストエディタに移動すると、
                        // キーボードとUIが被ってしまう（自動スクロールが作動しない）
                        // やってる処理は、現在のUIからフォーカスを外して、次UIにフォーカスを移動していること

                        // そこで、delay(500)をかけてから、移動させるように
                        // 下記処理を組み込む

                        // 問題点：delay(500)が適切かわからない、環境依存でできたりできなかったりする可能性あり
                        // エミュレータでPCキーボードのtab移動すると、キーボードとUIが被る現象が発生する（本来の用途ではないので問題にならなそうだが...）
                        // もっと良い解決策募集
                        scope.launch {
                            focusManager.clearFocus()
                            delay(500)
                            focusRequester.requestFocus()
                        }
                    }
                )
            )
        }
        Box(
            modifier = Modifier
                .weight(0.45f)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            LabelAndTextEdit(
                modifier = Modifier
                    .padding(start = 48.dp, end = 48.dp, bottom = 12.dp)
                    .focusRequester(focusRequester),
                labelResourceId = R.string.text_edit_password_label,
                text = password.value,
                onValueChange = {
                    val value = it.replace(
                        Constants.INPUT_VALIDATE_REGEX.toRegex(),
                        ""
                    )
                    if (value.length <= Constants.NAME_MAX_LENGTH) {
                        password.value = value
                    }
                },
                placeholderText = stringResource(id = R.string.text_edit_password_place_holder_text),
                visualTransformation = if (isShowPassword.value) {
                    VisualTransformation.None
                } else {
                    // isShowPassword.value = trueのとき、●●●の形式で表示させる
                    PasswordVisualTransformation(
                        mask = '\u25cf'
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                trailingIcon = {
                    Icon(
                        // アイコンクリックで表示する文字を変化させる「普通の表示 or ●●●表示」
                        modifier = Modifier.clickable {
                            isShowPassword.value = !isShowPassword.value
                        },
                        imageVector = if (isShowPassword.value) {
                            Icons.Default.Face
                        } else {
                            Icons.Default.Check
                        },
                        contentDescription = null,
                        tint = Color(0xFF5E789F),
                    )
                },
            )
        }
        Box(
            modifier = Modifier
                .weight(0.1f)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 48.dp, end = 48.dp, bottom = 12.dp),
            ) {
                if (uiState.isValidateMessage) {
                    CFText(
                        text = stringResource(id = uiState.checkUserMessageId),
                        color = Color.Red,
                    )
                }
                CFButton(
                    text = stringResource(id = R.string.text_edit_check_user_validate_button_text),
                    onClick = { checkUser(user.value) }
                )
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun LabelAndTextEdit(
    modifier: Modifier = Modifier,
    @StringRes labelResourceId: Int,
    text: String = Constants.INIT_STRING_VALUE,
    onValueChange: (String) -> Unit = {},
    placeholderText: String = Constants.INIT_STRING_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Column {
        CFLabelText(
            modifier = modifier,
            labelText = stringResource(id = labelResourceId),
        )
        CFTextEdit(
            modifier = modifier,
            value = text,
            onValueChange = onValueChange,
            placeholderText = placeholderText,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = trailingIcon,
        )
    }
}

@Preview
@Composable
private fun TemplateScreenBodyPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            TextEditScreenBody()
        }
    }
}
