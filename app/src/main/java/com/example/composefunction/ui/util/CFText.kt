package com.example.composefunction.ui.util

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun CFText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = 16.sp,
    lineHeight: TextUnit = 24.sp,
    fontWeight: FontWeight? = FontWeight.W500,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = fontSize,
        lineHeight = lineHeight,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = maxLines,
        minLines = minLines,
        overflow = TextOverflow.Visible
    )
}

@Composable
fun CFLabelText(
    modifier: Modifier,
    labelText: String
) {
    CFText(
        modifier = modifier,
        text = labelText,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.W300
    )
}

@Preview
@Composable
private fun CFTextPreview() {
    CFText(
        modifier = Modifier
            .background(Color.White),
        text = "サンプル"
    )
}
