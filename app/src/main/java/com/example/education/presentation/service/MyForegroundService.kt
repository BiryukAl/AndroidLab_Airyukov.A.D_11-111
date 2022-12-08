package com.example.education.presentation.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.education.presentation.utils.NotificationsHandler

class MyForegroundService : Service() {

    private var notificationsHandler: NotificationsHandler? = null
    private var notification: Notification? = null
    private val foregroundServiceId: Int = 10101

    private var glide: RequestManager? = null

     val url = "https://s1.1zoom.ru/b6755/973/Cats_Kittens_Grey_Glance_Wicker_basket_517968_2048x1152.jpg"

    var isLoadFailed = false

    inner class MainBinder : Binder() {
        fun downloadBind() {
            downloadImg()

            notification = notificationsHandler?.createSimpleNotification(
                headerText = "com.example.education",
                messageText = if (isLoadFailed){"DONE"}else{"ERROR"}
            )
            notificationsHandler?.getNotificationManager()
                ?.notify(foregroundServiceId, notification)

        }

        fun isDownload(): Boolean {
            return isLoadFailed
        }

        fun stopService(){
            stopForeground(true)
            stopSelf()
        }

    }

    override fun onCreate() {
        super.onCreate()
        glide = Glide.with(this@MyForegroundService)
        notificationsHandler = NotificationsHandler(context = this)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (notification == null) {


            notification = notificationsHandler?.createSimpleNotification(
                headerText = "com.example.education",
                messageText = ""
            )
            startForeground(foregroundServiceId, notification)
        }

        intent?.extras?.let { bundle ->
            bundle.getString(SERVICE_ACTION_TAG)?.let { action ->
                when (action) {
                    STOP -> {
                        stopForeground(true)
                        stopSelf()
                    }
                    else -> {}
                }
            }
        }
        return START_STICKY
    }

    fun downloadImg(){
            glide?.load(url)
                ?.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                ?.listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        isLoadFailed = false;
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        isLoadFailed = true;
                        return true
                    }
                })
                ?.preload(300, 300)
    }

    override fun onBind(intent: Intent?): IBinder = MainBinder()

    override fun onDestroy() {
        notificationsHandler = null
        glide = null
        super.onDestroy()
    }

    companion object {
        const val SERVICE_ACTION_TAG = "SERVICE_ACTION_TAG"
        const val STOP = "stop"
        const val DOWNLOAD_IMG = "download"

    }
}