package com.karry.chaotic

enum class ChaoticType(val type: Int) {
    LOGISTIC(0),
    SIN(1)
}

class ChaoticCypher private constructor(
    private var perm: ChaoticType,
    private var sub: ChaoticType,
    private var diff: ChaoticType,

) {
    private var mode: Int = 0
    private lateinit var key: ByteArray

    class Builder() {
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

    fun init(mode: Int, key: ByteArray) {
        this.mode = mode
        this.key = key
    }

    fun init(mode: Int, key: String) {
        this.mode = mode
        this.key = key.toByteArray()
    }

    fun doFinal(data: ByteArray): ByteArray {
        if (mode == ENCRYPT_MODE) {
            return ChaoticCypher(perm, sub, diff).encrypt(data, key, perm, sub, diff)
        }
        return ChaoticCypher(perm, sub, diff).decrypt(data, key, perm, sub, diff)
    }

    private external fun encrypt(data: ByteArray, key: ByteArray, perm: ChaoticType, sub: ChaoticType, diff: ChaoticType): ByteArray
    private external fun decrypt(data: ByteArray, key: ByteArray, perm: ChaoticType, sub: ChaoticType, diff: ChaoticType): ByteArray

    companion object {
        // Used to load the 'chaotic' library on application startup.
        init {
            System.loadLibrary("chaotic")
        }

        const val ENCRYPT_MODE = 0
        const val DECRYPT_MODE = 1
    }
}
