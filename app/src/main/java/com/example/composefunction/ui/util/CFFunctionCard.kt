package com.example.composefunction.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composefunction.ui.model.FunctionCard

@Composable
fun CFFunctionCard(
    functionCard: FunctionCard,
    navigateToFunction: (String) -> Unit = {},
    isFavorite: Boolean = false,
//    favoriteState: MutableState<Boolean> = mutableStateOf(false),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(2.dp))
            .border(width = 1.dp, color = Color(0xFFE8E8EE))
            .padding(vertical = 8.dp)
            .then(
                if (functionCard.isRead.value) {
                    Modifier
                        .background(Color(0xFFD3D3D3))
                } else {
                    Modifier
                        .background(Color(0xFFFFFFFF))
                }
            )
            .clickable { navigateToFunction(functionCard.functionCardRoute) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isFavorite) {
            FavoriteIconButton(
                onClick = {
                    functionCard.isFavorite.value = !functionCard.isFavorite.value
                },
                isFavorite = functionCard.isFavorite.value
            )
        }
        FunctionInfoText(functionCard.functionCardName)
    }
}

@Composable
private fun FavoriteIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isFavorite: Boolean = false,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        val icon = if (isFavorite) Icons.Default.Favorite
        else Icons.Default.FavoriteBorder
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier.size(16.dp, 16.dp),
        )
    }
}

@Composable
private fun FunctionInfoText(
    functionText: String,
) {
    CFText(text = functionText)
}

@Preview
@Composable
private fun CFFunctionCardPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CFFunctionCard(
                functionCard = SampleFunctionCard,
                navigateToFunction = {},
            )
        }
    }
}

@Preview
@Composable
private fun CFFunctionCardOnFavoritePreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CFFunctionCard(
                functionCard = SampleFunctionCard,
                navigateToFunction = {},
                isFavorite = true,
            )
        }
    }
}


val SampleFunctionCard = FunctionCard(
    functionCardId = 1,
    functionCardName = "サンプル機能",
    functionCardRoute = "機能ルート"
)
