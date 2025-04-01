package com.example.composefunction.ui.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
) {
    val state = rememberHistoryScreenState()

    HistoryScreenBody(
        modifier = modifier,
        uiState = state.uiState,
    )
}

@Composable
private fun HistoryScreenBody(
    modifier: Modifier = Modifier,
    uiState: HistoryScreenUiState = HistoryScreenUiState(),
) {

}

@Preview
@Composable
private fun TemplateScreenBodyPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            HistoryScreenBody()
        }
    }
}
