package com.snowdango.bijouk.presenter.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.PlaybackStateCompat
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
    private val mediaStyle = MediaNotificationCompat.DecoratedMediaCustomViewStyle()
        .setShowActionsInCompactView(1)

    @RequiresPermission(Manifest.permission.MEDIA_CONTENT_CONTROL)
    override fun onCreate() {
        super.onCreate()
        viewModel = RemoteMediaViewModel(
            object : RemoteMediaViewModel.Callback {
                override fun onMetadataUpdated() {
                    updateNotification()
                }

                override fun onFinish() {
                    stopForeground(STOP_FOREGROUND_REMOVE)
                    stopSelf()
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
        when (intent?.action) {
            ACTION_INIT -> {
                intent.extras?.let {
                    baseUrl = it.getString(BASE_URL_KEY)
                    token = it.getString(TOKEN_KEY)
                    if (baseUrl != null && token != null) {
                        viewModel.connect(baseUrl!!, token!!)
                    }
                }
            }

            ACTION_PLAY_PAUSE -> {
                viewModel.playPause()
            }

            ACTION_NEXT -> {
                viewModel.next()
            }

            ACTION_PREVIOUS -> {
                viewModel.prev()
            }

            else -> {
                // Handle other actions if needed
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
            NotificationManager.IMPORTANCE_NONE,
        )
        notificationChannel.description = "Notification channel for Cider Remote Media Service"
        notificationChannel.enableVibration(false)
        notificationChannel.setSound(null, null)
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
            .setStyle(mediaStyle.setMediaSession(viewModel.mediaSession.sessionToken))
            .setSmallIcon(R.drawable.ic_launcher)
            .setOngoing(false)
            .also {
                it.addAction(
                    R.drawable.ic_previous,
                    "Previous",
                    actionIntent(ACTION_PREVIOUS)
                )
                it.addAction(
                    when (viewModel.currentPlaybackState.state) {
                        PlaybackStateCompat.STATE_PLAYING -> {
                            R.drawable.ic_pause
                        }

                        PlaybackStateCompat.STATE_PAUSED -> {
                            R.drawable.ic_play
                        }

                        else -> {
                            R.drawable.ic_play_disable
                        }
                    },
                    "Play/Pause",
                    if (viewModel.currentPlaybackState.state == PlaybackStateCompat.STATE_PLAYING ||
                        viewModel.currentPlaybackState.state == PlaybackStateCompat.STATE_PAUSED
                    ) {
                        actionIntent(ACTION_PLAY_PAUSE)
                    } else {
                        null
                    }
                )
                it.addAction(
                    R.drawable.ic_skip,
                    "Next",
                    actionIntent(ACTION_NEXT)
                )
            }
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        val notification = notificationBuilder.build()
        notificationManager.notify(notifyId, notification)
        return notification
    }

    private fun actionIntent(action: String): PendingIntent {
        return PendingIntent.getService(
            this,
            0,
            Intent(this, RemoteMediaService::class.java).setAction(action),
            PendingIntent.FLAG_UPDATE_CURRENT,
        )
    }

    companion object {
        const val ACTION_INIT = "ACTION_INIT"
        const val ACTION_PLAY_PAUSE = "ACTION_PLAY_PAUSE"
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_PREVIOUS = "ACTION_PREVIOUS"

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
            intent.setAction(ACTION_INIT)
            startForegroundService(context, intent)
        }
    }
}
