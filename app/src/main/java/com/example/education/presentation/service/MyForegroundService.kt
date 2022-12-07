package com.example.education.presentation.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.IBinder
import com.example.education.presentation.utils.NotificationsHandler

class MyForegroundService : Service() {

    private var notificationsHandler: NotificationsHandler? = null
    private var notification: Notification? = null
    private val foregroundServiceId: Int = 10101
    private val FIRST_ACTION_REQUEST_CODE = 1224


    inner class MainBinder : Binder() {
        fun setImage(bitmap: Bitmap?, text: String?) {
            notification = notificationsHandler?.createNotifyService(
                headerText = "com.example.education",
                messageText = text,
                bitmap = bitmap,
            )
            notificationsHandler?.getNotificationManager()
                ?.notify(foregroundServiceId, notification)
        }

        fun setTextNotify(text: String?) {
            notification = notificationsHandler?.createNotifyService(
                headerText = "com.example.education",
                messageText = text,
                bitmap = null
            )
            notificationsHandler?.getNotificationManager()
                ?.notify(foregroundServiceId, notification)
        }

    }

    override fun onCreate() {
        super.onCreate()
        notificationsHandler = NotificationsHandler(context = this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (notification == null) {


            notification = notificationsHandler?.createNotifyService(
                headerText = "com.example.education",
                messageText = "test",
                bitmap = null
            )

            startForeground(foregroundServiceId, notification)
        } else {
            intent?.extras?.let { bundle ->
                bundle.getString(SERVICE_ACTION_TAG)?.let { action ->

                }
            }
        }

        intent?.extras?.let { bundle ->
            bundle.getString(SERVICE_ACTION_TAG)?.let { action ->
                when (action) {
                    SERVICE_ACTION_STOP -> {
                        stopForeground(true)
                        stopSelf()
                    }
                    else -> {}
                }
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = MainBinder()

    override fun onDestroy() {
        notificationsHandler = null
        super.onDestroy()
    }

    companion object {
        const val SERVICE_ACTION_TAG = "SERVICE_ACTION_TAG"
        const val SERVICE_ACTION_STOP = "stop"

    }
}