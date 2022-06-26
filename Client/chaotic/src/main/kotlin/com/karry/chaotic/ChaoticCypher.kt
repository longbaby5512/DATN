package com.karry.chaotic

enum class ChaoticType(val type: Int) {
    LOGISTIC(0),
    SIN(1);

    override fun toString(): String {
        return if (this == LOGISTIC) {
            "Logistic"
        } else {
            "Sin"
        }
    }
}

class ChaoticCypher private constructor(
    private var perm: ChaoticType,
    private var sub: ChaoticType,
    private var diff: ChaoticType
) {
    private var mode: Mode = Mode.ENCRYPT
    private lateinit var key: ByteArray

    enum class Mode(val mode: Int) {
        ENCRYPT(ENCRYPT_MODE),
        DECRYPT(DECRYPT_MODE);
    }

    class Builder {
        private var perm: ChaoticType = ChaoticType.LOGISTIC
        private var sub: ChaoticType = ChaoticType.LOGISTIC
        private var diff: ChaoticType = ChaoticType.LOGISTIC


        fun setPermutationAlgorithm(chaos: ChaoticType): Builder {
            perm = chaos
            return this
        }

        fun setSubstitutionAlgorithm(chaos: ChaoticType): Builder {
            sub = chaos
            return this
        }

        fun setDiffusionAlgorithm(chaos: ChaoticType): Builder {
            diff = chaos
            return this
        }

        fun build(): ChaoticCypher {
            return ChaoticCypher(perm, sub, diff)
        }
    }

    fun init(mode: Mode, key: ByteArray) {
        this.mode = mode
        this.key = key
    }


    fun init(mode: Mode, key: String) {
        this.mode = mode
        this.key = key.toByteArray()
    }

    suspend fun doFinal(data: ByteArray): ByteArray {
        if (mode == Mode.ENCRYPT) {
            return ChaoticCypherNative().encrypt(data, key, perm, sub, diff)
        }
        return ChaoticCypherNative().decrypt(data, key, perm, sub, diff)
    }

    override fun toString(): String {
        return "ChaoticCypher(perm=$perm, sub=$sub, diff=$diff)"
    }

    companion object {
        init {
            System.loadLibrary("chaotic")
        }

        private const val ENCRYPT_MODE = 0
        private const val DECRYPT_MODE = 1
    }
}


private class ChaoticCypherNative {

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
