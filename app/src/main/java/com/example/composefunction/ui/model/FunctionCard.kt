package com.example.composefunction.ui.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.composefunction.constants.Constants


/**
 * カード単体情報
 */
// TODO : 機能カード作って遷移処理をもたせる
// TODO : 作成日付、更新日付、機能名、機能概要、などなど
// TODO : 必要最低限に必要な情報を記載する
// TODO : 未読、既読の状態変化をできるようにする
// TODO : お気に入りの状態変化をできるようにする
// TODO : DataStoreに保持する予定

data class FunctionCard(
    val functionCardId: Int = Constants.INVALID_VALUE,
    val functionCardName: String = Constants.INIT_STRING_VALUE,
    val functionCardRoute: String = Constants.INIT_STRING_VALUE,
) {
    val isFavorite: MutableState<Boolean> = mutableStateOf(false)
    val isRead: MutableState<Boolean> = mutableStateOf(false)
}
