package com.example.composefunction.ui.util

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import timber.log.Timber
import java.security.KeyStore
import java.security.MessageDigest
import java.util.Collections
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import kotlin.experimental.xor

// ファイル暗号化処理（"引用"しただけなので未解析）
// （インターフェースの作り方、関数の作り方要参考）
interface Crypto {

    fun prepare(): Boolean
    fun encrypt(plainText: String): String?
    fun encrypt(plainByte: ByteArray): ByteArray?
    fun encrypt(plainIntValue: Int): Int?
    fun decrypt(encryptedText: String?): String?
    fun decrypt(encryptedByte: ByteArray?): ByteArray?
    fun decrypt(encryptedIntValue: Int?): Int?
    fun hashString(src: String): String

    companion object {
        var instance: Crypto = CryptoImpl()

        fun initialize(inst: Crypto) {
            instance = inst
        }
    }
}

class CryptoImpl : Crypto {
    private val keyProvider = "AndroidKeyStore"
    private val keyAlias = "CFKeyAlias"
    private val algorithm = "AES/CBC/PKCS7Padding"

    private val hashAlgorithm = "SHA-256"
    private val hexChars = "0123456789ABCDEF"
    private val intEncKey = "I"
    private val ivBytes: ByteArray = hexChars.toByteArray()

    private lateinit var keyStore: KeyStore
    private lateinit var ivParameterSpec: IvParameterSpec
    private lateinit var key: SecretKey
    private lateinit var intEncVal: ByteArray
    private var prepared = false

    private val encryptedCache: MutableMap<String, String> =
        Collections.synchronizedMap(mutableMapOf())
    private val decryptedCache: MutableMap<String, String> =
        Collections.synchronizedMap(mutableMapOf())


    // 準備関数
    override fun prepare(): Boolean {
        runCatching {
            // 準備完了している場合は無条件でtrueを返す
            if (prepared) {
                return true
            }
            keyStore = KeyStore.getInstance(keyProvider)
            keyStore.load(null)

            if (!keyStore.containsAlias(keyAlias)) {
                val keyGenerator =
                    KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, keyProvider)
                keyGenerator.init(
                    KeyGenParameterSpec.Builder(
                        keyAlias,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .setRandomizedEncryptionRequired(false)
                        .build()
                )
                keyGenerator.generateKey()
            }
            key = keyStore.getKey(keyAlias, null) as SecretKey
            val cipher = Cipher.getInstance(algorithm)
            ivParameterSpec = IvParameterSpec(ivBytes)
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec)
            intEncVal = cipher.doFinal(intEncKey.toByteArray())
            prepared = true
            return true
        }.onFailure {
            Timber.e(it.message)
        }
        return false
    }

    override fun encrypt(plainText: String): String? {
        // キャッシュがある場合はそのままキャッシュを返す
        encryptedCache[plainText]?.let {
            return encryptedCache[plainText]
        }
        // キャッシュがない(null)の場合は、暗号化して返す
        // Byteに変換して暗号化する（encrypt(plainByte: ByteArray)を呼び出す）
        val bytes = encrypt(plainText.toByteArray(Charsets.UTF_8))
        bytes?.let {
            val ret = Base64.encodeToString(bytes, Base64.NO_WRAP) // 引数のplainTextを暗号化する
            encryptedCache[plainText] = ret // 暗号化したものをキャッシュに保存する
            decryptedCache[ret] = plainText // 復号化キャッシュに保存する（暗号化されたデータをKeyにして、暗号化前のデータを保存する）
            return ret
        }
        return null
    }

    override fun encrypt(plainByte: ByteArray): ByteArray? {
        // 準備ができてない場合、nullを返す
        if (!prepared) {
            return null
        }
        runCatching {
            val cipher = Cipher.getInstance(algorithm)
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec)
            return cipher.doFinal(plainByte)
        }.onFailure {
            Timber.e(it.message)
        }
        return null
    }

    override fun encrypt(plainIntValue: Int): Int? {
        // 準備ができてない場合、nullを返す
        if (!prepared) {
            return null
        }
        return intEncVal.let {
            plainIntValue.toBytes().mapIndexed { idx, value ->
                value.xor(it[idx])
            }.toByteArray().toInt()
        }
    }

    override fun decrypt(encryptedText: String?): String? {
        // 暗号化されたテキストがnullではないときに処理を継続する
        encryptedText?.let { text ->
            // 対象テキストが復号化キャッシュに保存されている場合は、キャッシュを返す
            decryptedCache[text]?.let {
                return decryptedCache[text]
            }
            // 対象テキストが復号化キャッシュに保存されていない場合は、復号化処理を行う
            // Byteに変換して復号化する（decrypt(encryptedByte: ByteArray?)を呼び出す）
            decrypt(Base64.decode(text, Base64.NO_WRAP))?.let {
                val ret = String(it)
                decryptedCache[text] = ret // 復号化したものをキャッシュに保存する
                encryptedCache[ret] = text // 暗号化化キャッシュに保存する（復号化化されたデータをKeyにして、複合化前のデータを保存する）
                return ret
            }
        }
        return null
    }

    override fun decrypt(encryptedByte: ByteArray?): ByteArray? {
        // 準備ができてない場合、nullを返す
        if (!prepared) {
            return null
        }
        // 暗号化バイトがnullの場合、nullを返す
        if (encryptedByte == null) {
            return null
        }

        runCatching {
            val cipher = Cipher.getInstance(algorithm)
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec)
            return cipher.doFinal(encryptedByte)
        }.onFailure {
            Timber.e(it.message)
        }
        return null
    }

    override fun decrypt(encryptedIntValue: Int?): Int? {
        // 準備ができてない場合、nullを返す
        if (!prepared) {
            return null
        }
        encryptedIntValue?.let { intValue ->
            return intEncVal.let {
                intValue.toBytes().mapIndexed { idx, value ->
                    value.xor(it[idx])
                }.toByteArray().toInt()
            }
        }
        return null
    }

    override fun hashString(src: String): String {
        val md = MessageDigest.getInstance(hashAlgorithm)
        val hashBytes = md.digest(src.toByteArray())
        val result = StringBuilder(hashBytes.size * 2)

        hashBytes.forEach {
            val i = it.toInt()
            result.append(hexChars[i shr 4 and 0x0F])
            result.append(hexChars[i and 0x0F])
        }
        return result.toString()
    }
}

// よくわからないので解析必要
// 多分、そのまま変換できないので、ループ文で1つずつ変換処理を行い、まとめた結果を返してる？
private fun Int.toBytes(): ByteArray {
    var value = this
    val result = ByteArray(4)
    for (i in result.indices) {
        result[i] = (value and 0xFF).toByte()
        value = value shr 8
    }
    return result
}

// よくわからないので解析必要
// 多分、そのまま変換できないので、ループ文で1つずつ変換処理を行い、まとめた結果を返してる？
private fun ByteArray.toInt(): Int {
    var result = 0
    for (i in indices) {
        result = result or (get(i).toByte().toInt() shl 8 * i)
    }
    return result
}
