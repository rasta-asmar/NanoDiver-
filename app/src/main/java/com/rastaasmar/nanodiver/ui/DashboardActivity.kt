package com.rastaasmar.nanodiver.ui

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rastaasmar.nanodiver.monitoring.SystemStatsReader
import kotlinx.coroutines.*

class DashboardActivity : AppCompatActivity() {
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val tv = TextView(this)
        layout.addView(tv)
        setContentView(layout)

        scope.launch {
            val stats = withContext(Dispatchers.IO) { SystemStatsReader(applicationContext).readDetailedStats() }
            tv.text = "CPU temp: ${'$'}{stats.cpuTemp ?: "n/a"}\nMem total: ${'$'}{stats.memTotal} KB\nMem free: ${'$'}{stats.memFree} KB\nOpen sockets: ${'$'}{stats.openSockets}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
