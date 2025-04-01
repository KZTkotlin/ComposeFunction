package com.example.composefunction.ui.util

import androidx.compose.ui.tooling.preview.Preview


// Preview表示をデバイス端末のサイズに設定できる（デバイス指定可能）
// 数値設定も可能（下記は数値設定のもの）
// 多言語対応なども可能（日本語、英語、中国語...など）
@Preview(
    showSystemUi = true,
    device = "spec:id=reference_phone,shape=Normal,width=500,height=1000,unit=dp,dpi=240",
)
annotation class PreviewComposeFunction
