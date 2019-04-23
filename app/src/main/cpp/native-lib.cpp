#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_mrecun_utils_BaseApp_stringSample(
        JNIEnv *env,
        jobject /* this */) {
    std::string clientId= "Sample String just for check JNI";
    return env->NewStringUTF(clientId.c_str());
}

