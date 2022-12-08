package com.example.education.presentation.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.education.R

class NotificationsHandler(private val context: Context) {


    private fun buildNotification(
        headerText: String?,
        messageText: String?,
        intent: PendingIntent,
    ) =
        NotificationCompat.Builder(context, DEFAULT_CHANEL_ID)
            .setSmallIcon(R.drawable.ic_first_page_black)
            .setContentTitle(headerText)
            .setContentText(messageText)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(intent)


    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun getNotificationManager(): NotificationManager {
        return notificationManager
    }


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

    fun createNotifyService(
        headerText: String?,
        messageText: String?,
        bitmap: Bitmap?,
    ): Notification {

        val nBilder = NotificationCompat.Builder(context, DEFAULT_CHANEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_settings_24)
            .setContentTitle(headerText)
            .setContentText(messageText)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))

        return nBilder.build()
    }

    fun notifySimpleNotification(
        id: Int,
        headerText: String?,
        messageText: String?,
    ) {
        notificationManager.notify(id,
            createSimpleNotification(headerText, messageText))
    }

    fun createSimpleNotification(
        headerText: String?,
        messageText: String?,
    ): Notification {
        return NotificationCompat.Builder(context, DEFAULT_CHANEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_settings_24)
            .setContentTitle(headerText)
            .setContentText(messageText)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }


    fun createNotification(headerText: String?, messageText: String?, intent: PendingIntent) {
        notificationManager.notify(TEST_NOTIFICATION_ID,
            buildNotification(headerText, messageText, intent).build())
    }


    fun createNotification(
        headerText: String?,
        messageText: String?,
        bigText: String?,
        intent: PendingIntent,
    ) {
        val notificationBuilder =
            buildNotification(headerText, messageText, intent)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("$messageText\n$bigText")) // У меня на телефоне, почему то когда открывается BigText, исчезает ContentText, я их и соединил)))



        notificationManager.notify(TEST_NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        private const val DEFAULT_CHANEL_ID = "SAMPLE_APP_DEFAULT_CHANNEL"
        private const val TEST_NOTIFICATION_ID = 200
        private const val ID_NOYIFY_SERVISE = 321

    }

}