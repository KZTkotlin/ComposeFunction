package com.example.composefunction.domain

import com.example.composefunction.data.repository.SampleRepository

// UseCase作成のサンプル
// リポジトリデータを処理してUIに渡すパイプ役のようなもの
// ここに、リポジトリ同士の処理や、型変換したリポジトリデータを返したりする
class UseCaseSample(
    private val sampleRepository: SampleRepository
) {
    // 下記のように書けば、コンストラクタを呼び出すだけでメソッドにアクセスできる（.invokeがいらなくなる）
    suspend operator fun invoke(
        update: () -> Unit, // 呼び出し元で、更新処理の中身を書く
    ) {
        sampleRepository.sampleUpdate()
    }
}
