package com.example.composefunction.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composefunction.navigation.functionItems
import com.example.composefunction.ui.model.FunctionCard

@Composable
fun CFFunctionCardList(
    modifier: Modifier = Modifier,
    isFavorite: Boolean = true,
    navigateToFunction: (String) -> Unit = {},
) {
    val functionCardList: MutableList<FunctionCard> = mutableListOf()
    functionItems.forEachIndexed { index, item ->
        functionCardList.add(
            FunctionCard(
                functionCardId = index,
                functionCardName = stringResource(id = item.labelId),
                functionCardRoute = item.route
            )
        )
    }
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = modifier
            .background(Color(0xCCFFFFFF))
            .padding(16.dp)
            .lazyColumnVerticalScrollbar(listState)
        ,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 8.dp)
    ) {
        items(functionCardList) { item ->
            CFFunctionCard(
                functionCard = item,
                navigateToFunction = navigateToFunction,
                isFavorite = isFavorite
            )
        }
    }
}

@Preview
@Composable
private fun CFFunctionCardListPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CFFunctionCardList()
        }
    }
}
