package com.karry.chaotic


enum class ChaoticType(val type: Int) {
    Logistic(0),
    Sin(1);

    override fun toString(): String {
        return if (this == Logistic) {
            "Logistic"
        } else {
            "Sin"
        }
    }
}

class ChaoticCypher private constructor(
    private val permType: ChaoticType = ChaoticType.Logistic,
    private val subType: ChaoticType = ChaoticType.Logistic,
    private val diffType: ChaoticType = ChaoticType.Logistic
) {

    private lateinit var key: ByteArray
    private var mode = ENCRYPT_MODE

    fun init(mode: Int, key: ByteArray) {
        this.mode = mode
        this.key = key
    }

    suspend fun doFinal(data: ByteArray): ByteArray {
        return if (mode == ENCRYPT_MODE) {
            encrypt(data)
        } else {
            decrypt(data)
        }
    }

    private suspend fun decrypt(data: ByteArray): ByteArray {
        return NativeLib().decrypt(data, key, permType, subType, diffType)
    }

    private suspend fun encrypt(data: ByteArray): ByteArray {
        return NativeLib().encrypt(data, key, permType, subType, diffType)
    }


    companion object {
        const val ENCRYPT_MODE = 0
        const val DECRYPT_MODE = 1

        private var INSTANCE: ChaoticCypher? = null


        fun getInstance(
            permType: ChaoticType = ChaoticType.Logistic,
            subType: ChaoticType = ChaoticType.Logistic,
            diffType: ChaoticType = ChaoticType.Logistic
        ): ChaoticCypher {
            if (INSTANCE == null) {
                INSTANCE = ChaoticCypher(permType, subType, diffType)
            }
            return INSTANCE!!
        }

    }
}

private class NativeLib {
    external suspend fun encrypt(
        data: ByteArray,
        key: ByteArray,
        perm: ChaoticType,
        sub: ChaoticType,
        diff: ChaoticType
    ): ByteArray

    external suspend fun decrypt(
        data: ByteArray,
        key: ByteArray,
        perm: ChaoticType,
        sub: ChaoticType,
        diff: ChaoticType
    ): ByteArray

    companion object {
        init {
            System.loadLibrary("chaotic")
        }
    }
}