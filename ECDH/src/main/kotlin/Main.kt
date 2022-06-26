fun main() {
    val alice = ECDH()
    val bob = ECDH()

    val alicePublicKey = alice.publicKey
    val bobPublicKey = bob.publicKey

    println("Alice's public key: ${alicePublicKey.toHexString()}")
    println("Bob's public key: ${bobPublicKey.toHexString()}")
    println()

    val alicePrivateKey = alice.privateKey
    val bobPrivateKey = bob.privateKey

    println("Alice's private key: ${alicePrivateKey.toHexString()}")
    println("Bob's private key: ${bobPrivateKey.toHexString()}")
    println()

    val aliceSharedSecret = ECDH.generateSharedSecret(
        theirPublicKey = bobPublicKey,
        ourPrivateKey = alicePrivateKey
    )

    val bobSharedSecret = ECDH.generateSharedSecret(
        theirPublicKey = alicePublicKey,
        ourPrivateKey = bobPrivateKey
    )

    println("Alice's shared secret: ${aliceSharedSecret.toHexString()}")
    println("Bob's shared secret: ${bobSharedSecret.toHexString()}")
    println()

    val aliceFinalKey = ECDH.generateFinalKey(
        theirPublicKey = bobPublicKey,
        ourPublicKey = alicePublicKey,
        sharedSecret = aliceSharedSecret
    )
    val bobFinalKey = ECDH.generateFinalKey(
        theirPublicKey = alicePublicKey,
        ourPublicKey = bobPublicKey,
        sharedSecret = bobSharedSecret
    )

    println("Alice's final key: ${aliceFinalKey.toHexString()}")
    println("Bob's final key: ${bobFinalKey.toHexString()}")
    println()
}

fun ByteArray.toHexString(): String {
    return joinToString("") { "%02x".format(it) }
}