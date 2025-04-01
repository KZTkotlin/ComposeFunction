package com.example.composefunction.ui.home

import com.example.composefunction.data.repository.TestSampleRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch


@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {
    private val viewModel = HomeScreenViewModel(
        sampleRepository = TestSampleRepository(),
        // テスト専用のdispatcherにしないと、JUnitのテストがうまく動作しないため、ViewModelのコンストラクタにdispatcherを入れてる
        dispatcher = UnconfinedTestDispatcher(),
    )

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("ViewModel Thread") // この名前でいいのかは不明

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun sampleRepository_Test() = runTest {
        val latch = CountDownLatch(1) // CountDownLatchを一言で言うと、他のスレッドの処理を待機する仕組みである。
        latch.countDown()
        /**
         * ここにテスト対象のviewModelのメソッドを呼び出す
         */

        advanceUntilIdle() //testScopeの中で待機していたコルーチンの処理が終わるまで待ってくれる
        latch.await()

//        ↓これらを用いて、テスト結果を比較して判定する
//        assertEquals()
//        assertFalse()
//        assertTrue()

    }
}
