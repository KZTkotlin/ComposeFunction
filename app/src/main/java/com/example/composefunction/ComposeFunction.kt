package com.example.composefunction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composefunction.appbar.CFBottomAppBar
import com.example.composefunction.appbar.CFTopAppBar
import com.example.composefunction.constants.Constants
import com.example.composefunction.navigation.CFNavHost
import com.example.composefunction.navigation.NavigationDestination

@Composable
fun ComposeFunction(
    modifier: Modifier = Modifier,
) {
    val navHostController = rememberNavController()
    val currentBackStack by navHostController.currentBackStackEntryAsState()
    val currentDestinationRoute =
        currentBackStack?.destination?.route ?: Constants.INIT_STRING_VALUE
    val startDestination = NavigationDestination.Home.route

    Scaffold(
        modifier = Modifier,
        topBar = {
            CFTopAppBar(currentDestinationRoute)
        },
        bottomBar = {
            CFBottomAppBar(
                onClickBottomAppBarItem = { navigateScreen: NavigationDestination ->
                    navHostController.navigate(navigateScreen.route)
                },
                currentDestinationRoute = currentDestinationRoute,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            CFNavHost(
                navHostController = navHostController,
                startDestination = startDestination,
                modifier = modifier,
            )
        }
    }
}
