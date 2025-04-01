package com.example.composefunction.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composefunction.R
import com.example.composefunction.ui.util.CFButton
import com.example.composefunction.ui.util.CFDropDownList
import com.example.composefunction.ui.util.CFText


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val state = rememberHomeScreenState()

    HomeScreenBody(
        modifier = modifier,
        uiState = state.uiState,
        onDisplayHiddenMessage = state::onDisplayHiddenMessage,
    )
}

@Composable
private fun HomeScreenBody(
    modifier: Modifier = Modifier,
    uiState: HomeScreenUiState = HomeScreenUiState(),
    onDisplayHiddenMessage: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CFText(
            text = stringResource(id = R.string.home_contents_text),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(60.dp))
        // TODO : 画面作る時間がもったいないので、暫定的にここに配置（時間あるときに、専用画面作ってね）
        CFDropDownList(
            modifier = Modifier.width(320.dp),
            dataList = listOf(
                "テスト１",
                "テスト２",
                "テスト３",
                "テスト４",
                "テスト５",
                "テスト６",
                "テスト７",
                "テスト８",
                "テスト９",
                "テスト１０",
                "テスト１",
                "テスト２",
                "テスト３",
                "テスト４",
                "テスト５",
                "テスト６",
                "テスト７",
                "テスト８",
                "テスト９",
                "テスト１０",
            )
        )

        Spacer(modifier = Modifier.height(60.dp))
        CFButton(
            text = stringResource(id = R.string.home_button_text),
            onClick = onDisplayHiddenMessage
        )
        Spacer(modifier = Modifier.height(60.dp))
        if (uiState.isHiddenMessage) {
            CFText(
                text = stringResource(id = R.string.home_contents_hidden_text),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenBodyPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreenBody()
        }
    }
}
