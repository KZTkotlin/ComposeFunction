package com.example.composefunction.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composefunction.constants.Constants


// TODO : 記事
@Composable
fun CFDropDownList(
    modifier: Modifier = Modifier,
    hasNotSelected: Boolean = false,
    dataList: List<String> = listOf(),
    value: String = Constants.INIT_STRING_VALUE,
    onClickValue: (String) -> Unit = {},
    readOnly: Boolean = true,
    placeholderText: String = Constants.INIT_STRING_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit = {},
) {
    // ドロップダウンのサイズをコンポーネント事に変えるときは、BoxWithConstraintsで囲う必要がある
    BoxWithConstraints() {
        val dropDownMenuWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
        CFTextEdit(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            readOnly = readOnly,
            placeholderText = placeholderText,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = {
                CFDropDownTrainingIcon(
                    hasNotSelected = hasNotSelected,
                    dataList = dataList,
                    onClickValue = onClickValue,
                    dropDownMenuWidth = dropDownMenuWidth,
                )
            }
        )
    }
}

@Composable
private fun CFDropDownTrainingIcon(
    modifier: Modifier = Modifier,
    hasNotSelected: Boolean = false,
    dataList: List<String> = listOf(),
    onClickValue: (String) -> Unit = {},
    dropDownMenuWidth: Dp,
) {
    val expanded = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    IconButton(onClick = { expanded.value = true }) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = if (dataList.isNotEmpty()) Color.Unspecified else Color(0xFFD1D1D1)
        )
    }
    if (dataList.isEmpty()) return

    // 角丸するために、MaterialThemeで括る必要あり
    MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(8.dp))) {
        DropdownMenu(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF94B3D0),
                    shape = RoundedCornerShape(8.dp)
                )
                .background(
                    color = Color(0xFFF9FAFB),
                    shape = RoundedCornerShape(8.dp)
                )
                .width(dropDownMenuWidth)
                .requiredHeightIn(max = 360.dp)
                .verticalScrollBar(scrollState),
            scrollState = scrollState,
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            dataList.forEachIndexed { index, value ->
                val isNotSelected = (index == 0 && hasNotSelected)
                DropdownMenuItem(
                    text = {
                        CFText(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState()),
                            text = value,
                            color = if (isNotSelected) Color(0xFF6A7073) else Color(0xFF404040)
                        )
                    },
                    onClick = {
                        val listValue = if (isNotSelected) Constants.INIT_STRING_VALUE else value
                        onClickValue(listValue)
                        expanded.value = false
                    },
                    modifier = Modifier
                        .height(48.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun CFDropDownListPreview() {
    val dataList = listOf(
        "value1",
        "value2",
        "value3",
        "value4",
        "value5",
    )
    val value = ""
    CFDropDownList(
        dataList = dataList,
        value = value,
        onClickValue = {},
    )
}
