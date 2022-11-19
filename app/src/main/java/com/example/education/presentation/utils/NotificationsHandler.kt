package com.example.education.presentation.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.education.R

class NotificationsHandler(private val context: Context) {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(DEFAULT_CHANEL_ID) == null) {
                val notificationChannel = NotificationChannel(
                    DEFAULT_CHANEL_ID,
                    context.getString(R.string.default_chanel),
                    NotificationManager.IMPORTANCE_DEFAULT)

                notificationManager.createNotificationChannel(notificationChannel)
            }
        }
    }

    private fun buildNotification(headerText: String?, messageText: String?, intent: PendingIntent) =
        NotificationCompat.Builder(context, DEFAULT_CHANEL_ID)
            .setSmallIcon(R.drawable.ic_first_page_black)
            .setContentTitle(headerText)
            .setContentText(messageText)
            .setContentIntent(intent)

    fun createNotification(headerText: String?, messageText: String?, intent: PendingIntent) {
        notificationManager.notify(200, buildNotification(headerText, messageText, intent).build())
    }


    fun createNotification(headerText: String?, messageText: String?, bigText: String?, intent: PendingIntent) {
        val notificationBuilder =
            buildNotification(headerText, messageText, intent)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("$messageText\n$bigText")) // У меня на телефоне, почему то когда открывается BigText, исчезает ContentText, я их и соединил)))



        notificationManager.notify(200, notificationBuilder.build())
    }

    companion object {
        private const val DEFAULT_CHANEL_ID = "SAMPLE_APP_DEFAULT_CHANNEL"
    }

}