package com.example.composefunction.data.repository.impl

import android.database.sqlite.SQLiteFullException
import coil.network.HttpException
import com.example.composefunction.data.repository.CFRepositoryError
import kotlinx.serialization.SerializationException
import okio.FileNotFoundException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

// throwableの種類だけメモっておく
class CFRepositoryErrorImpl : CFRepositoryError {
    constructor(throwable: Throwable) {
        when (throwable) {
            is HttpException -> {} // APIレスポンスが正常でない(ステータスコード200以外)場合にthrowされる
            is SocketTimeoutException -> {} // APIのタイムアウトが発生した場合にthrowされる
            is ConnectException -> {} // サーバーとの接続が失敗した場合にthrowされる
            is UnknownHostException -> {} // サーバーとの接続が失敗した場合にthrowされる（電波が弱い場合に発生する場合あり）
            is SocketException -> {} // サーバーとの接続が失敗した場合にthrowされる（電波が弱い場合に発生する場合あり）
            // IllegalArgumentExceptionとしてハンドリングされる可能性があるため
            // IllegalArgumentExceptionより優先的にハンドリングする
            is SerializationException -> {} // JSONパースエラー
            // 主にリポジトリの処理内で無効値を検出した場合にセットされる
            is IllegalStateException -> {} // check() をパスできなかった場合にthrowされる（check()はRepositoryのテストで使用するメソッド）
            // 主にDBの桁数チェックなどにより無効地を検出した場合にセットされる
            is IllegalArgumentException -> {} // require() をパスできなかった場合にthrowされる（require()はわからない）
            is SQLiteFullException -> {} // DB登録中にストレージ不足となった場合にthrowされる
            is FileNotFoundException -> {} // ストレージ不足でファイルが保存されず、そのファイルを参照した場合にthrowされる
            else -> {} // 予期せぬエラー
            // 独自のException()クラスを生成して、リポジトリのthrowするところで指定すれば、独自のエラークラスを呼び出せう
            // 例）class BadApiResponseException : Exception()
            // if (isBadApi) { throw BadApiResponseException() }
            // .. など
        }
    }

    override fun getErrorCode(): String {
        TODO("Not yet implemented")
    }
}
