package com.example.composefunction.ui.cameraX

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.Settings
import android.util.Size
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.composefunction.R
import com.example.composefunction.ui.util.CFButton
import com.example.composefunction.ui.util.CFLoadingIndicator
import com.example.composefunction.ui.util.CFText
import com.example.composefunction.ui.util.Crypto
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CameraXScreen(
    modifier: Modifier = Modifier,
) {
    val state = rememberCameraXScreenState()

    CameraXScreenBody(
        modifier = modifier,
        uiState = state.uiState,
        onTakeImage = state::updateImageFilePath
    )
}

@Composable
private fun CameraXScreenBody(
    modifier: Modifier = Modifier,
    uiState: CameraXScreenUiState = CameraXScreenUiState(),
    onTakeImage: (String) -> Unit = {},
) {
    // カメラ画面を表示するための必要情報
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val resolutionSelector = ResolutionSelector.Builder()
        .setResolutionStrategy(
            // カメラの解像度
            ResolutionStrategy(
                Size(720, 960),
                ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER_THEN_LOWER
            )
        )
        .build()

    val imageCapture = remember {
        ImageCapture.Builder()
            .setResolutionSelector(resolutionSelector)
            .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
            .build()
    }

    val previewView = remember {
        PreviewView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }

    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    DisposableEffect(Unit) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = androidx.camera.core.Preview.Builder()
            .setResolutionSelector(resolutionSelector)
            .build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        preview.setSurfaceProvider(previewView.surfaceProvider)
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        // コンポーズが破棄されるとき、cameraProviderにbindした各種lifecycleを破棄するようにする
        onDispose {
            cameraProvider.unbindAll()
        }
    }

    // UI本体
    if (uiState.isLoading) {
        CFLoadingIndicator()
    } else {
        CameraViewOrPermissionView(
            modifier = modifier,
            uiState =  uiState,
            context = context,
            previewView = previewView,
            imageCapture = imageCapture,
            onTakeImage = onTakeImage,
        )
    }

}

@Composable
private fun CameraViewOrPermissionView(
    modifier: Modifier,
    uiState: CameraXScreenUiState,
    context: Context,
    previewView: PreviewView,
    imageCapture: ImageCapture,
    onTakeImage: (String) -> Unit,
) {
    // アプリの権限設定ダイアログを表示する
    // カメラの権限が許可されたら、カメラ画面を表示
    // カメラの権限が許可されなかったら、許可を促す画面を表示

    // 権限設定をチェックステータスにして保持する
    val permissionCameraState = remember { mutableStateOf(PermissionCameraState.Checking) }
    // 権限確認ダイアログを表示するためのランチャー
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {}
    // 権限の種類を設定（カメラに設定）
    val permission = Manifest.permission.CAMERA

    // 現在のライフサイクルを取得
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    // ライフサイクルを監視する
    val lifecycleObserver = remember {
        LifecycleEventObserver { _, event ->
            // イベントのライフサイクルがonResumeのとき（onResume：画面が表示されたときのライフサイクル）
            if (event == Lifecycle.Event.ON_RESUME) {
                // アプリ自身の権限をチェックする
                val result = context.checkSelfPermission(permission)
                when (result) {
                    PackageManager.PERMISSION_GRANTED -> // 権限が許可されているとき
                        permissionCameraState.value = PermissionCameraState.Granted

                    PackageManager.PERMISSION_DENIED -> // 権限が許可されていないとき
                        permissionCameraState.value = PermissionCameraState.Denied

                    else -> // その他のケース
                        permissionCameraState.value = PermissionCameraState.Checking
                }
                // カメラ権限が許可する以外のとき、カメラ権限を許可する確認ダイアログを表示させる
                if (permissionCameraState.value != PermissionCameraState.Granted) {
                    launcher.launch(permission)
                }
            }
        }
    }
    // 現在のライフサイクルと、onResumeのライフサイクルイベントをKeyにする
    DisposableEffect(lifecycle, lifecycleObserver) {
        // 現在のライフサイクルにonResumeのライフサイクルイベントを監視する
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            // コンポーズが破棄されるときに、現在のライフサイクルからonResumeのライフサイクルイベントを破棄する
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    // カメラ権限の状態によって、表示する画面をハンドリングする
    when (permissionCameraState.value) {
        PermissionCameraState.Checking -> {} // カメラ権限チェック状態のときは何も表示しない
        PermissionCameraState.Granted -> { // カメラ権限が許可されるときは、カメラ画面を表示する
            CameraView(
                modifier = modifier,
                uiState = uiState,
                context = context,
                previewView = previewView,
                imageCapture = imageCapture,
                onTakeImage = onTakeImage,
            )
        }

        PermissionCameraState.Denied -> { // カメラ権限が許可されていないときは、許可を促す画面を表示する
            NoPermission(context)
        }
    }
}

@Composable
private fun CameraView(
    modifier: Modifier = Modifier,
    uiState: CameraXScreenUiState,
    context: Context,
    previewView: PreviewView,
    imageCapture: ImageCapture,
    onTakeImage: (String) -> Unit,
) {
    val shutterPressed = remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // カメラ画面
        Box(
            modifier = Modifier
                .weight(0.9f) // カメラ画面とそれ以外を「0.1:0.9比率」で配置する
        ) {
            AndroidView(
                modifier = Modifier
                    .clipToBounds(), // このプロパティがないと、カメラ画面のサイズが変動しない
                factory = { previewView }, // カメラ画面のView情報
            )
            // カメラ撮影ボタンが押されたときに、画面全体を黒くして、撮影されることを視覚的に通知する
            if (shutterPressed.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(0.1f) // カメラ画面とそれ以外を「0.1:0.9比率」で配置する
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CFButton(
                    text = stringResource(id = R.string.camera_shutter_button_text),
                    onClick = {
                        shutterPressed.value = true // カメラ画面を黒くする
                        takeImage(
                            uiState = uiState,
                            context = context,
                            imageCapture = imageCapture,
                            onTakeImage = onTakeImage,
                            onTakeFinished = { shutterPressed.value = false }
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun NoPermission(context: Context) {
    Column(
        modifier = Modifier
            .padding(horizontal = 60.dp)
    ) {
        Spacer(Modifier.weight(1f))
        CFText(text = stringResource(id = R.string.camera_no_permission_text))
        Spacer(modifier = Modifier.width(4.dp))
        CFButton(
            text = stringResource(id = R.string.camera_no_permission_button_text),
            // ボタンクリックで、アプリの設定画面に遷移させる
            onClick = {
                val intent = Intent().apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK // SingleTonにする
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS // アプリの詳細設定に遷移する
                    data = Uri.parse("package:${context.packageName}") // アプリの情報をセット
                }
                context.startActivity(intent)
            }
        )
    }
}

private fun takeImage(
    uiState: CameraXScreenUiState,
    context: Context,
    imageCapture: ImageCapture,
    onTakeImage: (String) -> Unit,
    onTakeFinished: () -> Unit,
) {
    val internalOutputDirectory = context.filesDir // ファイル格納パス
    // 日本の現在時刻情報を指定フォーマットで文字列化 + 拡張子の.jpgでファイル名を生成
    val photoFile = File(
        internalOutputDirectory,
        SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss-SSS", Locale.JAPANESE
        ).format(System.currentTimeMillis()) + ".jpg"
    )

    imageCapture.takePicture(
        ContextCompat.getMainExecutor(context),
        // このコールバック関数は、カメラ撮影して写真を保存処理を手動で行L関数
        // （ググっても出てこない。ググって出てくるのは、保存までされるコールバック関数ばかり）
        // ImageCaptureのメソッドの定義にジャンプして、それっぽいものを引っ張ってきた
        // このコールバック関数を用いれば、暗号化が実装可能
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onError(exception: ImageCaptureException) {
                onTakeFinished() // 撮影終了の処理（カメラ画面を黒く表示した処理の解除）
                Timber.e("撮影失敗：$exception")
                super.onError(exception)
            }

            override fun onCaptureSuccess(image: ImageProxy) {
                onTakeFinished() // 撮影終了の処理（カメラ画面を黒く表示した処理の解除）
                // ImageProxyから暗号化したJPEGデータのByteArrayに変換する
                val buffer = image.planes[0].buffer
                val bytes = ByteArray(buffer.remaining())
                buffer.get(bytes)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val jpegByteArray = byteArrayOutputStream.toByteArray()
                val encryptJpegByteArray = Crypto.instance.encrypt(jpegByteArray)

                encryptJpegByteArray?.let {
                    runCatching {
                        photoFile.writeBytes(it) // 暗号化したJPEGデータをストレージに保存する
                    }.onSuccess {
                        // すでに写真が撮られてファイルパスが格納されているとき
                        if (uiState.imageFilePath.isNotEmpty()) {
                            val path = Paths.get(uiState.imageFilePath)

                            runCatching {
                                val result = Files.deleteIfExists(path) // ファイルパスのデータを削除する
                                if (result) {
                                    Timber.i("写真削除処理：成功")
                                } else {
                                    Timber.e("写真削除処理：ファイルが存在しません")
                                }
                            }.onFailure {
                                Timber.e("写真削除処理：失敗($it)")
                            }
                        }
                        onTakeImage(photoFile.path)
                    }.onFailure {
                        Timber.e("写真保存処理：失敗($it)")
                    }
                } // encryptJpegByteArrayがnullのときに処理したいなら、ここに「?:」で処理内容を書く
                image.close()
                super.onCaptureSuccess(image)
            }
        }
    )
}

@Preview
@Composable
private fun CameraXScreenBodyPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            CameraXScreenBody()
        }
    }
}
