package com.example.education.presentation.utils

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.education.presentation.MainActivity

class SampleBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val pendingIntent = PendingIntent.getActivity(context,
            0,
            Intent(context, MainActivity::class.java),
            pendingIntentFlag)

        NotificationsHandler(context!!).createNotification(
            intent?.extras?.getString(HEADER_TEXT),
            intent?.extras?.getString(MESSAGE_TEXT),
            intent?.extras?.getString(BIG_TEXT),
            pendingIntent
        )


    }


    companion object {
        const val HEADER_TEXT = "HEADER_TEXT"
        const val MESSAGE_TEXT = "MESSAGE_TEXT"
        const val BIG_TEXT = "BIG_TEXT"
    }
}