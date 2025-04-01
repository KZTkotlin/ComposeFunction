package com.example.composefunction.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composefunction.constants.Constants
import com.example.composefunction.navigation.NavigationDestination
import com.example.composefunction.navigation.bottomAppBarItems

// TODO : 記事
@Composable
fun CFBottomAppBar(
    onClickBottomAppBarItem: (NavigationDestination) -> Unit = {},
    currentDestinationRoute: String = Constants.INIT_STRING_VALUE,
) {
    BottomAppBar(
        contentPadding = PaddingValues(0.dp)
    ) {
        val oneBottomTabWidth =
            (LocalConfiguration.current.screenWidthDp / bottomAppBarItems.size).dp
        bottomAppBarItems.forEach { item ->
            val backGroundColor = if (currentDestinationRoute == item.route) Color(0xF5FFFFFF)
            else Color(0xB8F5F5F5)

            Column(
                modifier = Modifier
                    .width(oneBottomTabWidth)
                    .background(color = backGroundColor)
            ) {
                if (currentDestinationRoute == item.route) {
                    HorizontalDivider(
                        thickness = 4.dp,
                        color = Color.Red
                    )
                } else {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color(0xFFFFFFFF)
                    )
                }
                this@BottomAppBar.NavigationBarItem(
                    selected = (currentDestinationRoute == item.route),
                    onClick = {
                        if (currentDestinationRoute != item.route) {
                            onClickBottomAppBarItem(item)
                        }
                    },
                    icon = {
                        item.icon?.let {
                            Icon(
                                modifier = Modifier,
                                imageVector = it,
                                contentDescription = null,
                            )
                        }
                    },
                    label = {
                        Text(
                            modifier = Modifier,
                            text = stringResource(id = item.labelId)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Red,
                        selectedTextColor = Color.Red,
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = Color(0xFF5E789F),
                        unselectedTextColor = Color(0xFF5E789F),
                    )
                )
            }
        }
    }
}
