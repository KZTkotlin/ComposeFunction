package com.example.composefunction

import android.app.Application
import android.content.Context
import com.example.composefunction.ui.util.Crypto
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ComposeFunctionApplication : Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        configureTimber()
        if (!Crypto.instance.prepare()) { // Crypto使用の前準備関数を呼び出す
            Timber.e("Crypto prepare failure!!")
        }
    }

    companion object {
        private var instance: ComposeFunctionApplication? = null

        fun getContext(): Context? {
            return instance?.applicationContext
        }
    }

    // デバッグビルドのみ、Timberコードを機能させる関数
    private fun configureTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
