package com.example.ordermanagementcake.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val orderId = intent.getStringExtra(AlarmSchedulerImpl.EXTRA_ORDER_ID) ?: return
        val message = intent.getStringExtra(AlarmSchedulerImpl.EXTRA_MESSAGE) ?: return

        Log.d("NotificationReceiver", "Received alarm for Order: $orderId")

        val notificationHelper = NotificationHelper(context)
        notificationHelper.showNotification(orderId, message)
    }
}
