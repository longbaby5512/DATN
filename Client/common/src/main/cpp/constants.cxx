#include <jni.h>
#include <string>


extern "C" JNIEXPORT jstring JNICALL
Java_com_karry_common_Constants_getBaseUrl(
        JNIEnv *env,
        jobject /* this */) {
    return env->NewStringUTF("https://oh-my-chat-server.herokuapp.com/");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_karry_common_Constants_getSharedPrefName(
        JNIEnv *env,
        jobject /* this */
) {
    return env->NewStringUTF("secret_shared_prefs");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_karry_common_Constants_getKeyToken(
        JNIEnv *env,
        jobject /* this */
) {
    return env->NewStringUTF("access_token");
}

extern "C" JNIIMPORT jstring JNICALL
Java_com_karry_common_Constants_getKeyUser(JNIEnv *env, jobject) {
    return env->NewStringUTF("current_user");
}