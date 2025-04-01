package com.example.composefunction.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composefunction.ui.cameraX.CameraXScreen
import com.example.composefunction.ui.contents.ContentsScreen
import com.example.composefunction.ui.favorite.FavoriteScreen
import com.example.composefunction.ui.history.HistoryScreen
import com.example.composefunction.ui.home.HomeScreen
import com.example.composefunction.ui.settings.SettingsScreen
import com.example.composefunction.ui.textedit.TextEditScreen

@Composable
fun CFNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        // ホーム画面
        composable(route = NavigationDestination.Home.route) {
            HomeScreen()
        }
        // コンテンツ画面
        composable(route = NavigationDestination.Contents.route) {
            ContentsScreen(
                navigateToFunction = { route ->
                    navHostController.navigate(route)
                }
            )
        }
        // お気に入り画面
        composable(route = NavigationDestination.Favorite.route) {
            FavoriteScreen()
        }
        // 履歴画面
        composable(route = NavigationDestination.History.route) {
            HistoryScreen()
        }
        // 設定画面
        composable(route = NavigationDestination.Settings.route) {
            SettingsScreen()
        }
        // コンテンツ関数一覧
        // テキストエディタ画面
        composable(route = NavigationDestination.TextEdit.route) {
            TextEditScreen()
        }
        // カメラ画面
        composable(route = NavigationDestination.Camera.route) {
            CameraXScreen()
        }
    }
}
