package com.rastaasmar.nanodiver.monitoring

object NativeBridge {
    init { System.loadLibrary("nanodiver-native") }
    external fun readProcUptime(): String
}
