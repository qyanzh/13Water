package com.example.water13

import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Calendar.getInstance().apply {
            timeInMillis = 1570608076 * 1000L
            println(this)
        }
    }
}
