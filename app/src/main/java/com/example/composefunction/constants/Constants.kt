package com.example.composefunction.constants


object Constants {
    const val INIT_STRING_VALUE = ""
    const val INVALID_VALUE = -1

    // メールアドレスやパスワードの正規表現の文字列（どういう意味なのかはよくわかっていない）
    const val INPUT_VALIDATE_REGEX = """[^a-zA-Z0-9! "#$&'()~|`{+*}<>?_\-\\@\[;:\],./=]"""
    const val NAME_MAX_LENGTH = 128
}
