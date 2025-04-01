package com.example.composefunction.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter


// 画像に対してピンチイン、ピンチアウトできるようにするための関数らしい
@Composable
fun ZoomMap(
    modifier: Modifier = Modifier,
    painter: Painter? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFD9D9D9))
            .graphicsLayer(
                clip = true
            ),
    ) {
        painter?.let {
            // ZoomImage() を作成して呼び出す
        }
    }
}
