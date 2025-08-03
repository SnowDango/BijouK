package com.snowdango.bijouk.presenter.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.startForegroundService
import com.snowdango.bijouk.presenter.R
import androidx.media.app.NotificationCompat as MediaNotificationCompat


class RemoteMediaService : Service() {

    private var baseUrl: String? = null
    private var token: String? = null

    private lateinit var viewModel: RemoteMediaViewModel
    private var notifyId = 1321321
    private val channelId = "cider_remote_media_service"
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationManager: NotificationManager

    @RequiresPermission(Manifest.permission.MEDIA_CONTENT_CONTROL)
    override fun onCreate() {
        super.onCreate()
        viewModel = RemoteMediaViewModel(
            object : RemoteMediaViewModel.Callback {
                override fun onMetadataUpdated() {
                    updateNotification()
                }
            }
        )
        createNotificationManager()
        startForegroundService()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        intent?.extras?.let {
            baseUrl = it.getString(BASE_URL_KEY)
            token = it.getString(TOKEN_KEY)
            if (baseUrl != null && token != null) {
                viewModel.connect(baseUrl!!, token!!)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        intent.extras?.let {
            baseUrl = it.getString(BASE_URL_KEY)
            token = it.getString(TOKEN_KEY)
        }
        return null
    }

    private fun createNotificationManager() {
        notificationChannel = NotificationChannel(
            channelId,
            "Cider Remote Media Service",
            NotificationManager.IMPORTANCE_DEFAULT,
        )
        notificationChannel.description = "Notification channel for Cider Remote Media Service"
        notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    @RequiresPermission(Manifest.permission.MEDIA_CONTENT_CONTROL)
    @SuppressLint("UseCompatLoadingForDrawables", "RestrictedApi")
    private fun startForegroundService() {
        val notification = updateNotification()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            startForeground(notifyId, notification)
        } else {
            startForeground(notifyId, notification, FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        }
    }

    private fun updateNotification(): Notification {
        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
            .setCategory(Notification.CATEGORY_SOCIAL)
            .setStyle(
                MediaNotificationCompat.DecoratedMediaCustomViewStyle()
                    .setMediaSession(viewModel.mediaSession.sessionToken)
                    .setShowActionsInCompactView(1)
            )
            .setSmallIcon(R.drawable.ic_launcher)
            .setOngoing(true)
            .addAction(R.drawable.ic_previous, "Previous", null)
            .addAction(R.drawable.ic_play, "Play", null)
            .addAction(R.drawable.ic_skip, "Next", null)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        val notification = notificationBuilder.build()
        notificationManager.notify(notifyId, notification)
        return notification
    }


    companion object {
        const val BASE_URL_KEY = "baseUrl"
        const val TOKEN_KEY = "token"

        fun startService(
            context: Context,
            baseUrl: String,
            token: String,
        ) {
            val intent = Intent(context, RemoteMediaService::class.java)
            intent.putExtra("baseUrl", baseUrl)
            intent.putExtra("token", token)
            startForegroundService(context, intent)
        }
    }

}