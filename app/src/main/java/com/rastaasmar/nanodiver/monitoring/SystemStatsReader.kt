package com.rastaasmar.nanodiver.monitoring

import android.content.Context
import java.io.File

data class DetailedStats(val memTotal: Long, val memFree: Long, val cpuTemp: String?, val openSockets: Int)

class SystemStatsReader(private val ctx: Context) {

    fun readDetailedStats(): DetailedStats {
        var memTotal = 0L
        var memFree = 0L
        try {
            File("/proc/meminfo").forEachLine { line ->
                when {
                    line.startsWith("MemTotal") -> memTotal = line.split(Regex("\\s+"))[1].toLong()
                    line.startsWith("MemFree") -> memFree = line.split(Regex("\\s+"))[1].toLong()
                }
            }
        } catch (e: Exception) {}
        val cpuTemp = readCpuTemp()
        val openSockets = countOpenSockets()
        return DetailedStats(memTotal, memFree, cpuTemp, openSockets)
    }

    private fun readCpuTemp(): String? {
        val temps = listOf("/sys/class/thermal/thermal_zone0/temp", "/sys/class/thermal/thermal_zone1/temp")
        temps.forEach { p ->
            try {
                val f = File(p)
                if (f.exists()) {
                    val raw = f.readText().trim().toLongOrNull()
                    if (raw != null) {
                        return if (raw > 1000) "${'$'}{raw/1000}°C" else "${'$'}raw"
                    }
                }
            } catch (e: Exception) {}
        }
        return null
    }

    private fun countOpenSockets(): Int {
        try {
            val tcp = File("/proc/net/tcp")
            if (tcp.exists()) {
                return tcp.readLines().size - 1
            }
        } catch (e: Exception) {}
        return 0
    }
}
