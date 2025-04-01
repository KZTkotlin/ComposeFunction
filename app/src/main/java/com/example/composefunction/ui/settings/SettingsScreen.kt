package com.example.composefunction.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composefunction.BuildConfig
import com.example.composefunction.ui.util.CFText

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
) {
    val state = rememberSettingsScreenState()

    SettingsScreenBody(
        modifier = modifier,
        uiState = state.uiState
    )
}

@Composable
private fun SettingsScreenBody(
    modifier: Modifier = Modifier,
    uiState: SettingsScreenUiState = SettingsScreenUiState(),
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CFText(text = BuildConfig.VERSION_NAME)
    }
}

@Preview
@Composable
private fun SettingsScreenBodyPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SettingsScreenBody()
        }
    }
}
