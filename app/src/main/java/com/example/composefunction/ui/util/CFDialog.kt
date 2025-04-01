package com.example.composefunction.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CFDialog(
    modifier: Modifier = Modifier,
    isOpenValue: MutableState<Boolean>,
    dismissRequestFlg: Boolean = false,
    onClick: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            isOpenValue.value = dismissRequestFlg
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = true
        ),
        content = {
            // ダイアログに表示するコンテンツを指定する

            // コンテンツ例
            // メッセージ
            // 画像（ImageBitmap / ImageVector / Painter）
            // WebView
        }
    )
}

// WebViewの注意点
// レイアウト作るときに、下記のレイヤータイプをセットしないと、表示がおかしくなる


//AndroidView(
//    factory = {
//        WebView(it).apply  {
//            setLayerType(View.LAYER_TYPE_HARDWARE, null)
//        }
//    }
//)
