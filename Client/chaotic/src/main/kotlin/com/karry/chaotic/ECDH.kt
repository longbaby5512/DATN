package com.karry.chaotic

import java.nio.ByteBuffer
import java.security.*
import java.security.spec.ECGenParameterSpec
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.KeyAgreement

class ECDH {
    private val keyPair: KeyPair

    init {
        val keyPairGenerator = KeyPairGenerator.getInstance(KEY_PAIR_GENERATOR_ALGORITHM)
        keyPairGenerator.initialize(ECGenParameterSpec(CURVE_NAME))
        keyPair = keyPairGenerator.generateKeyPair()
    }

    val publicKey: ByteArray
        get() = keyPair.public.encoded

    val privateKey: ByteArray
        get() = keyPair.private.encoded

    companion object {
        private const val HASH_ALGORITHM = "SHA256"
        private const val KEY_AGREEMENT_ALGORITHM = "ECDH"
        private const val KEY_PAIR_GENERATOR_ALGORITHM = "EC"
        private const val CURVE_NAME = "secp256r1"

        @Throws(
            NoSuchAlgorithmException::class,
            InvalidKeySpecException::class,
            InvalidKeyException::class,
            IllegalStateException::class
        )
        @JvmStatic
        fun generateSharedSecret(theirPublicKey: ByteArray, ourPrivateKey: ByteArray): ByteArray {
            val keyFactory = KeyFactory.getInstance(KEY_PAIR_GENERATOR_ALGORITHM)
            val theirPublicKeySpec = X509EncodedKeySpec(theirPublicKey)
            val ourPrivateKeySpec = PKCS8EncodedKeySpec(ourPrivateKey)
            val publicKey = keyFactory.generatePublic(theirPublicKeySpec)
            val privateKey = keyFactory.generatePrivate(ourPrivateKeySpec)
            val keyAgreement = KeyAgreement.getInstance(KEY_AGREEMENT_ALGORITHM)
            keyAgreement.init(privateKey)
            keyAgreement.doPhase(publicKey, true)
            return keyAgreement.generateSecret()
        }

        @Throws(
            NoSuchAlgorithmException::class,
            InvalidKeyException::class,
            IllegalStateException::class
        )
        @JvmStatic
        fun generateFinalKey(
            theirPublicKey: ByteArray,
            ourPublicKey: ByteArray,
            sharedSecret: ByteArray
        ): ByteArray {
            val hash = MessageDigest.getInstance(HASH_ALGORITHM)
            hash.update(sharedSecret)
            val keys = listOf(ByteBuffer.wrap(theirPublicKey), ByteBuffer.wrap(ourPublicKey))
            keys.sortedWith { a, b -> a.compareTo(b) }.forEach { hash.update(it) }
            return hash.digest()
        }
    }
}