package com.example.composefunction.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.composefunction.R
import com.example.composefunction.constants.Constants
import com.example.composefunction.navigation.navigationDestinationItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CFTopAppBar(
    currentDestinationRoute: String = Constants.INIT_STRING_VALUE
) {
    val titleId = remember { mutableIntStateOf(Constants.INVALID_VALUE) }
    titleId.intValue = getTitleId(currentDestinationRoute)
    TopAppBar(
        title = {
            Text(
                modifier = Modifier,
                text = stringResource(id = titleId.intValue)
            )
        },
        modifier = Modifier,
        actions = {}, // TODO : ハンバーガーメニューやる
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0x99FFFFFF),
            titleContentColor = Color(0xFF353B41),
            navigationIconContentColor = Color(0xFF404040),
            actionIconContentColor = Color(0xFF404040),
        ),
    )
}

// TODO: 記事
private fun getTitleId(route: String): Int {
    for (item in navigationDestinationItems) {
        if (item.route == route) {
            return item.titleResourceId
        }
    }
    return R.string.empty_title
}
