package com.example.composefunction.ui.template

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TemplateScreen(
    modifier: Modifier = Modifier,
) {
    val state = rememberTemplateScreenState()

    TemplateScreenBody(
        modifier = modifier,
        uiState = state.uiState,
    )
}

@Composable
private fun TemplateScreenBody(
    modifier: Modifier = Modifier,
    uiState: TemplateScreenUiState = TemplateScreenUiState(),
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
            TemplateScreenBody()
        }
    }
}
