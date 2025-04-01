package com.example.composefunction.ui.util

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun Modifier.verticalScrollBar(
    scrollState: ScrollState,
    scrollBarWidth: Dp = 7.dp,
    minScrollBarHeight: Dp = 9.dp,
    scrollBarColor: Color = Color.LightGray,
    cornerRadius: Dp = 4.dp,
): Modifier = this.then(
    drawWithContent {
        drawContent()
        val height: Float = this.size.height - scrollState.maxValue
        val scrollBarHeight: Float =
            max(height * (height / this.size.height), minScrollBarHeight.toPx())
        val scrollPercent: Float = scrollState.value.toFloat() / scrollState.maxValue
        val scrollBarOffsetY: Float = scrollState.value + (height - scrollBarHeight) * scrollPercent

        drawRoundRect(
            color = scrollBarColor,
            topLeft = Offset(this.size.width - scrollBarWidth.toPx(), scrollBarOffsetY),
            size = Size(scrollBarWidth.toPx(), scrollBarHeight),
            cornerRadius = CornerRadius(cornerRadius.toPx())
        )
    }
)
