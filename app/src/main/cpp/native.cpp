#include <jni.h>
#include <string>
#include <fstream>
#include <sstream>

extern "C" JNIEXPORT jstring JNICALL
Java_com_rastaasmar_nanodiver_monitoring_NativeBridge_readProcUptime(JNIEnv* env, jclass) {
    std::ifstream f("/proc/uptime");
    std::string s;
    if (f.is_open()) {
        std::getline(f, s);
        f.close();
    } else {
        s = "unavailable";
    }
    return env->NewStringUTF(s.c_str());
}
