package com.example.composefunction.ui.favorite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

// TODO : お気に入り情報はDataStoreに格納する
// TODO : お気に入り情報をクリアできるようにする
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
) {
}

@Composable
private fun FavoriteScreenBody(
    modifier: Modifier = Modifier,
    uiState: FavoriteScreenUiState = FavoriteScreenUiState(),
) {

}

@Preview
@Composable
private fun FavoriteScreenBodyPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FavoriteScreenBody()
        }
    }
}
