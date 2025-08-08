package com.rastaasmar.nanodiver.monitoring

import java.io.File

class ProcReader {
    fun listProcesses(): List<Pair<Int, String>> {
        val proc = File("/proc")
        val list = mutableListOf<Pair<Int,String>>()
        if (!proc.exists() || !proc.isDirectory) return list
        proc.listFiles()?.forEach { f ->
            if (f.isDirectory) {
                val pid = f.name.toIntOrNull()
                if (pid != null) {
                    try {
                        val cmdline = File(f, "cmdline")
                        val name = if (cmdline.exists()) cmdline.readText().trim().ifEmpty { "[no-cmd]" } else "[no-cmd]"
                        list.add(Pair(pid, name))
                    } catch (e: Exception) {}
                }
            }
        }
        return list.sortedBy { it.first }
    }
}
