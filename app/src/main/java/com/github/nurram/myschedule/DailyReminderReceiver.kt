package com.github.nurram.myschedule

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.github.nurram.myschedule.view.main.MainActivity
import java.util.*


class DailyReminderReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val DAILY_BROADCAST_ID = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        showNotification(
            context,
            context.getString(R.string.wake_up),
            context.getString(R.string.check_schedule)
        )
    }

    private fun showNotification(context: Context, title: String, msg: String) {
        val channelId = "channel"
        val name = "my channel"

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notification =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(msg)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_date_range_24dp)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            builder.setAutoCancel(true)

            notification.createNotificationChannel(channel)
            notification.notify(DAILY_BROADCAST_ID, builder.build())
        }
    }

    fun setAlarm(context: Context, id: Int) {
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminderReceiver::class.java)
        intent.putExtra(EXTRA_ID, id)

        val calendar = Calendar.getInstance()
        val pendingIntent: PendingIntent?

        val sharedPref = context.getSharedPreferences("alarmPref", MODE_PRIVATE)
        val isAlarmSet = sharedPref.getBoolean("isAlarmSet", false)

        if (!isAlarmSet) {
            calendar.set(Calendar.HOUR_OF_DAY, 5)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            pendingIntent = PendingIntent.getBroadcast(
                context,
                DAILY_BROADCAST_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            manager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )

            sharedPref.edit {
                putBoolean("isAlarmSet", true)
            }
        }
    }

//    private fun cancelAlarm(context: Context, id: Int) {
//        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, DailyReminderReceiver::class.java)
//
//        val pendingIntent =
//            PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_NO_CREATE)
//
//        if (pendingIntent != null) {
//            manager.cancel(pendingIntent)
//        }
//    }
}