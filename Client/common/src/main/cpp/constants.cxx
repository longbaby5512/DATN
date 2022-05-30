#include <jni.h>
#include <string>


extern "C" JNIEXPORT jstring JNICALL
Java_com_karry_constants_Constants_getBaseUrl(
        JNIEnv* env,
        jobject /* this */) {
    const std::string BASE_URL = "http://192.168.0.106:3000";
    return env->NewStringUTF(BASE_URL.c_str());
}