package com.example.composefunction.ui.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

@Composable
fun Modifier.lazyColumnVerticalScrollbar(
    state: LazyListState
): Modifier {
    return drawWithContent {
        drawContent()
        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index

        firstVisibleElementIndex?.let {
            val scrollableItems =
                state.layoutInfo.totalItemsCount - state.layoutInfo.visibleItemsInfo.size
            val scrollbarHeight = this.size.height / scrollableItems
            val scrollbarOffsetY = ((this.size.height - scrollbarHeight) * it) / scrollableItems

            drawRoundRect(
                color = Color(0xFFF5F5F5),
                topLeft = Offset(x = this.size.width - 12f, y = scrollbarOffsetY),
                size = Size(width = 12f, height = 30f),
                cornerRadius = CornerRadius(x = 3f, y = 3f),
                alpha = 1f,
            )
        }
    }
}
