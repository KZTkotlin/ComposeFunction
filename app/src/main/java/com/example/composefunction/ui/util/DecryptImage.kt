package com.example.composefunction.ui.util

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.composefunction.constants.Constants
import timber.log.Timber
import java.io.File


// 画像ファイルパスから、復号化する処理
// SVGファイルに関しては、Coilを返せば複合できる（ここが苦労したところの目玉）
@Composable
fun decryptImage(
    context: Context,
    imageFilePath: String,
): Painter? {
    var painter: Painter? = null
    val decryptImageByteArray: MutableState<ByteArray?> = remember { mutableStateOf(null) }
    val beforeImageFilePath = remember { mutableStateOf(Constants.INIT_STRING_VALUE) }

    runCatching {
        // 指定の画像ファイルパスをすでに処理している場合は、処理をしない。
        // 処理していないときのみ、if文の中の処理を実行する
        if (decryptImageByteArray.value == null || imageFilePath != beforeImageFilePath.value) {
            val imageByteArray = File(imageFilePath).readBytes()
            decryptImageByteArray.value = Crypto.instance.decrypt(imageByteArray)
        }

        // SVGとそれ以外で処理ハンドリング
        if (imageFilePath.substringAfterLast(".", "") == "svg") {
            decryptImageByteArray.value?.let { byteArray ->
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(byteArray)
                        .size(coil.size.Size.ORIGINAL)
                        .decoderFactory(SvgDecoder.Factory())
                        .build()
                )
            }
        } else {
            decryptImageByteArray.value?.let { byteArray ->
                val imageBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                imageBitmap?.let { bitmap ->
                    painter =
                        remember(bitmap.asImageBitmap()) { BitmapPainter(bitmap.asImageBitmap()) }
                }
            }
        }
    }.onFailure {
        Timber.e(it.message)
    }
    // 引数で渡された画像ファイルパスを保持して、同じファイルパスの場合は処理しないようにする
    beforeImageFilePath.value = imageFilePath

    return painter
}
