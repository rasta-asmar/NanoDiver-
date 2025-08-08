package com.rastaasmar.nanodiver.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.rastaasmar.nanodiver.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            val dashboardBtn = Button(context).apply {
                text = "Dashboard"
                setOnClickListener { startActivity(Intent(this@MainActivity, DashboardActivity::class.java)) }
            }
            addView(dashboardBtn)
            val startServiceBtn = Button(context).apply {
                text = "Start Monitor Service"
                setOnClickListener {
                    startService(Intent(this@MainActivity, com.rastaasmar.nanodiver.monitoring.SystemMonitorService::class.java))
                }
            }
            addView(startServiceBtn)
        }
        setContentView(layout)
    }
}
