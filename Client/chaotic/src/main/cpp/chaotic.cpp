#include <jni.h>
#include <string>
#include <vector>
#include <android/log.h>
#include "Utils.h"
#include "ChaoticCypher.h"


bytes asByteVector(JNIEnv *env, jbyteArray src) {
    size_t size = env->GetArrayLength(src);
    auto *buf = new byte[size];
    bytes res;
    env->GetByteArrayRegion(src, 0, (jsize) size, reinterpret_cast<jbyte *>(buf));
    for (int i = 0; i < size; ++i) {
        res.push_back(buf[i]);
    }
    delete[] buf;
    return res;
}


jbyteArray asJByteArray(JNIEnv *env, const bytes &data) {
    auto *buff = (jbyte *) data.data();
    auto size = static_cast<jsize>(data.size());
    jbyteArray res = env->NewByteArray(size);
    env->SetByteArrayRegion(res, 0, size, buff);
    return res;
}

ChaoticType getType(JNIEnv *env, jobject obj) {
    if (obj == nullptr) {
        return ChaoticType::LOGISTIC;
    }
    jmethodID getTypeMethod = env->GetMethodID(env->FindClass("com/karry/chaotic/ChaoticType"),
                                               "getType", "()I");
    jint type = env->CallIntMethod(obj, getTypeMethod);
    return static_cast<ChaoticType>(type);
}

ChaoticCypher getCypher(JNIEnv *env, jobject permAlgm, jobject subAlgm, jobject diffAlgm) {
    return ChaoticCypher::Builder()
            .setPermutationAlgorithm(getType(env, permAlgm))
            .setSubstitutionAlgorithm(getType(env, subAlgm))
            .setDiffusionAlgorithm(getType(env, diffAlgm))
            .build();
}

extern "C" JNIEXPORT jbyteArray

JNICALL
Java_com_karry_chaotic_ChaoticCypherNative_encrypt(
        JNIEnv *env,
        jobject /* this */,
        jbyteArray dataJava,
        jbyteArray keyJava,
        jobject permAlgm,
        jobject subAlgm,
        jobject diffAlgm
) {

    auto data = asByteVector(env, dataJava);
    auto key = asByteVector(env, keyJava);

    auto cypher = getCypher(env, permAlgm, subAlgm, diffAlgm);

    cypher.init(ChaoticCypher::ENCRYPT, key);

    return asJByteArray(env, cypher.doFinal(data));
}


extern "C" JNIEXPORT jbyteArray

JNICALL
Java_com_karry_chaotic_ChaoticCypherNative_decrypt(
        JNIEnv *env,
        jobject /* this */,
        jbyteArray dataJava,
        jbyteArray keyJava,
        jobject permAlgm,
        jobject subAlgm,
        jobject diffAlgm
) {

    auto data = asByteVector(env, dataJava);
    auto key = asByteVector(env, keyJava);

    auto cypher = getCypher(env, permAlgm, subAlgm, diffAlgm);

    cypher.init(ChaoticCypher::DECRYPT, key);

    return asJByteArray(env, cypher.doFinal(data));
}