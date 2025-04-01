package com.example.composefunction.ui.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composefunction.ui.util.CFFunctionCardList

@Composable
fun ContentsScreen(
    modifier: Modifier = Modifier,
    navigateToFunction: (String) -> Unit = {},
) {
    val state = rememberContentsScreenState()

    ContentsScreenBody(
        modifier = modifier,
        uiState = state.uiState,
        navigateToFunction = navigateToFunction,
    )
}

@Composable
private fun ContentsScreenBody(
    modifier: Modifier = Modifier,
    uiState: ContentsScreenUiState = ContentsScreenUiState(),
    navigateToFunction: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CFFunctionCardList(
            modifier = Modifier,
            navigateToFunction = navigateToFunction
        )
    }
}

@Preview
@Composable
private fun ContentsScreenBodyPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ContentsScreenBody()
        }
    }
}
