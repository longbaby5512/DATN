package com.karry.chaotic

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    suspend fun addition_isCorrect() {
        val plainText = "Hello World"
        val key = "1234567890123456"
        val cypher = ChaoticCypher.Builder()
            .build()
        cypher.init(ChaoticCypher.Mode.ENCRYPT, key)
        val encrypted = cypher.doFinal(plainText.toByteArray())
        cypher.init(ChaoticCypher.Mode.DECRYPT, key)
        val decrypted = cypher.doFinal(encrypted)
        assertEquals(plainText, String(decrypted))

    }
}