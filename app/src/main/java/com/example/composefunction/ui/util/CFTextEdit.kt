package com.example.composefunction.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composefunction.constants.Constants

// TODO : 記事
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CFTextEdit(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    placeholderText: String = Constants.INIT_STRING_VALUE,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    BasicTextField(value = value,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(8.dp),
            ),
        onValueChange = onValueChange,
        readOnly = readOnly,
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 24.sp,
            color = Color(0xFF404040)
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = {
                    Text(
                        text = placeholderText,
                        color = Color(0xFF6A7073),
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.W400,
                        maxLines = 1,
                        overflow = TextOverflow.Visible,
                    )
                },
                trailingIcon = trailingIcon,
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
//                container = {
//                    OutlinedTextFieldDefaults.ContainerBox(
//                        enabled = true,
//                        isError = false,
//                        interactionSource = interactionSource,
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = Color(0xFF94B3D0),
//                            unfocusedBorderColor = Color(0xFF94B3D0),
//                        ),
//                        shape = RoundedCornerShape(8.dp)
//                    )
//                },
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                    top = 0.dp,
                    bottom = 0.dp,
                    start = 8.dp,
                    end = if (trailingIcon != null) 0.dp else 8.dp
                )
            )
        }
    )
}

@Preview
@Composable
private fun CFTextEditPreview() {
    CFTextEdit(
        value = "メールアドレス",
        onValueChange = {},
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        ),
        placeholderText = "プレースホルダー"
    )
}
