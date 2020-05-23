package com.github.nurram.myschedule.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.github.nurram.myschedule.DailyReminderReceiver
import com.github.nurram.myschedule.R
import kotlinx.android.synthetic.main.appbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val dailyReminderReceiver = DailyReminderReceiver()
        dailyReminderReceiver.setAlarm(this, DailyReminderReceiver.DAILY_BROADCAST_ID)
    }
}
