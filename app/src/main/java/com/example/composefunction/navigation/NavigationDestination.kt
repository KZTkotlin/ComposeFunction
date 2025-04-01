package com.example.composefunction.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.composefunction.R
import com.example.composefunction.constants.Constants

sealed class NavigationDestination(
    val route: String,
    val screenName: String = Constants.INIT_STRING_VALUE,
    @StringRes val titleResourceId: Int,
    @StringRes val labelId: Int,
    val icon: ImageVector? = null,
) {
    // ホーム画面
    data object Home : NavigationDestination(
        route = "Home",
        screenName = "Home",
        titleResourceId = R.string.home_title,
        labelId = R.string.home_label,
        icon = Icons.Default.Home
    )

    // コンテンツ画面
    data object Contents : NavigationDestination(
        route = "Contents",
        screenName = "Contents",
        titleResourceId = R.string.contents_title,
        labelId = R.string.contents_label,
        icon = Icons.Default.Build
    )

    // お気に入り画面
    data object Favorite : NavigationDestination(
        route = "Favorite",
        screenName = "Favorite",
        titleResourceId = R.string.favorite_title,
        labelId = R.string.favorite_label,
        icon = Icons.Default.Favorite,
    )

    // 履歴画面
    data object History : NavigationDestination(
        route = "History",
        screenName = "History",
        titleResourceId = R.string.history_title,
        labelId = R.string.history_label,
        icon = Icons.Default.Info,
    )

    // 設定画面
    data object Settings : NavigationDestination(
        route = "Settings",
        screenName = "Settings",
        titleResourceId = R.string.settings_title,
        labelId = R.string.settings_label,
        icon = Icons.Default.Settings
    )

    // コンテンツ関数一覧
    // テキストエディタ画面
    data object TextEdit : NavigationDestination(
        route = "TextEdit",
        screenName = "TextEdit",
        titleResourceId = R.string.text_edit_title,
        labelId = R.string.text_edit_label,
    )

    // カメラ画面
    data object Camera : NavigationDestination(
        route = "Camera",
        screenName = "Camera",
        titleResourceId = R.string.camera_title,
        labelId = R.string.camera_label,
    )
}

val navigationDestinationItems = listOf(
    NavigationDestination.Home,
    NavigationDestination.Contents,
    NavigationDestination.Favorite,
    NavigationDestination.History,
    NavigationDestination.Settings,
    NavigationDestination.TextEdit,
)
val bottomAppBarItems = listOf(
    NavigationDestination.Home,
    NavigationDestination.Contents,
    NavigationDestination.Favorite,
    NavigationDestination.History,
    NavigationDestination.Settings,
)
val functionItems = listOf(
    NavigationDestination.TextEdit,
    NavigationDestination.Camera
)
