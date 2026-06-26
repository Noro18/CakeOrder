package com.example.ordermanagementcake.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

interface AlarmScheduler {
    fun scheduleAlarm(orderId: String, timeInMillis: Long, message: String, isExactTime: Boolean)
    fun cancelAlarm(orderId: String, isExactTime: Boolean)
}

class AlarmSchedulerImpl(private val context: Context) : AlarmScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduleAlarm(orderId: String, timeInMillis: Long, message: String, isExactTime: Boolean) {
        // If time is in the past, don't schedule
        if (timeInMillis <= System.currentTimeMillis()) return

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra(EXTRA_ORDER_ID, orderId)
            putExtra(EXTRA_MESSAGE, message)
        }

        val requestCode = getRequestCode(orderId, isExactTime)
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            }
        } catch (e: SecurityException) {
            // Handle the case where SCHEDULE_EXACT_ALARM permission is not granted on Android 12+
            // You might want to fallback to a standard alarm or prompt the user
            alarmManager.setWindow(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                1000 * 60 * 10, // 10 minutes window
                pendingIntent
            )
        }
    }

    override fun cancelAlarm(orderId: String, isExactTime: Boolean) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val requestCode = getRequestCode(orderId, isExactTime)
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        alarmManager.cancel(pendingIntent)
    }

    private fun getRequestCode(orderId: String, isExactTime: Boolean): Int {
        // Create unique request codes based on whether it's the exact time or the 30-min warning
        val prefix = if (isExactTime) "E_" else "W_"
        return (prefix + orderId).hashCode()
    }

    companion object {
        const val EXTRA_ORDER_ID = "EXTRA_ORDER_ID"
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
    }
}
