package com.rastaasmar.nanodiver.monitoring

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SystemMonitorService : Service() {
    private val handler = Handler(Looper.getMainLooper())
    private val interval = 10_000L

    override fun onCreate() {
        super.onCreate()
        startForeground(2121, createNotification("NanoDiver monitoring"))
        handler.post(monitorRunnable)
    }

    private val monitorRunnable = object : Runnable {
        override fun run() {
            try {
                val stats = SystemStatsReader(applicationContext).readDetailedStats()
                val logDir = File(filesDir, "nanodiver_logs")
                if (!logDir.exists()) logDir.mkdirs()
                val f = File(logDir, "log.txt")
                val w = FileWriter(f, true)
                val line = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date()) +
                        " | memTotal=${'$'}{stats.memTotal} memFree=${'$'}{stats.memFree} cpu=${'$'}{stats.cpuTemp} sockets=${'$'}{stats.openSockets}\n"
                w.append(line); w.flush(); w.close()
            } catch (e: Exception) {}
            handler.postDelayed(this, interval)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(monitorRunnable)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification(text: String): Notification {
        val channelId = "nanodiver_monitor_channel"
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel(channelId, "NanoDiver Monitor", NotificationManager.IMPORTANCE_LOW)
            nm.createNotificationChannel(ch)
        }
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("NanoDiver")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .build()
    }
}
