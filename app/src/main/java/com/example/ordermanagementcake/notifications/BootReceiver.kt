package com.example.ordermanagementcake.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootReceiver", "Device rebooted. Need to reschedule alarms.")
            // TODO: In a later step, query the database for upcoming orders
            // and use AlarmScheduler to reschedule them.
        }
    }
}
