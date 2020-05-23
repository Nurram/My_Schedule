package com.github.nurram.myschedule

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun formatDate(day: Int, month: Int, year: Int): String =
        "$day/$month/$year"

    fun formatTime(hour: Int, minute: Int): String = "$hour:$minute"

    fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        val df = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())

        return df.format(date)
    }

    fun toTimeMillis(dateString: String): Long? {
        return SimpleDateFormat("dd/M/yyyy HH:mm", Locale.getDefault()).parse(dateString)?.time
    }

    fun dateStringFromMillis(millis: Long): String {
        val date = Date(millis)
        val df = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())

        return df.format(date)
    }

    fun timeStringFromMillis(millis: Long): String {
        val time = Date(millis)
        val df = SimpleDateFormat("HH:mm", Locale.getDefault())

        return df.format(time)
    }
}