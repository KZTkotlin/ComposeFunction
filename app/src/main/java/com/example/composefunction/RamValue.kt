package com.example.composefunction

// グローバルに使える変数
// ただし、エラーがでるので解消が必要
// Platform declaration clash: The following declarations have the same JVM signature (getRamValue()Ljava/lang/String;):
// Stringがだめっぽい。参考では、data classの型にしてた。

//interface RamValue {
//    var ramValue: String
//    fun getRamValue(): String
//    fun setRamValue(ramValue: String)
//}
//
//class RamValueImpl : RamValue {
//    override var ramValue: String = Constants.INIT_STRING_VALUE
//    override fun getRamValue(): String {
//        return ramValue
//    }
//
//    override fun setRamValue(ramValue: String) {
//        this.ramValue = ramValue
//    }
//}
