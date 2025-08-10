package com.snowdango.bijouk.presenter.notification

import android.content.Context
import android.media.MediaMetadata
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.util.Log
import coil3.ImageLoader
import coil3.request.ErrorResult
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.toBitmap
import com.snowdango.bijouk.model.cider.CiderRPCModel
import com.snowdango.bijouk.model.cider.CiderSocketModel
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.ui.image.cacheableImageRequest
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf


class RemoteMediaViewModel(
    private val callback: Callback,
) : KoinComponent {

    private val context: Context by inject()
    private val coroutineScope: CoroutineScope by inject()
    private val mediaSessionTag = "BijoukMediaSession"

    private var ciderRPCModel: CiderRPCModel? = null
    private var ciderSocketModel: CiderSocketModel? = null
    private val mutex: Mutex = Mutex()

    var currentMetadata: MediaMetadata = MediaMetadata.Builder()
        .putText(MediaMetadata.METADATA_KEY_TITLE, "No song playing")
        .putLong(MediaMetadata.METADATA_KEY_DURATION, 0L)
        .build()
    var currentPlaybackState: PlaybackState = PlaybackState.Builder()
        .setState(PlaybackState.STATE_NONE, 0L, 0f)
        .build()
    val mediaSession: MediaSession = MediaSession(context, mediaSessionTag).apply {
        isActive = false
    }


    private val socketConnectionEventListener =
        object : CiderSocketModel.SocketConnectionEventListener {
            override fun onConnect() {
                mediaSession.isActive = true
                initMetadata()
            }

            override fun onDisConnect() {
                mediaSession.isActive = false
                callback.onFinish()
            }
        }

    private val playBackEventListener = object : CiderSocketModel.PlayBackStatusEventListener {
        override fun onTimeChangeEvent(playBackTimeData: PlayBackTimeData) {
            updatePlaybackState(playBackTimeData)
            // TODO seek bar
        }

        override fun onStateChangeEvent(
            nowPlayData: NowPlayData?,
            playBackTimeData: PlayBackTimeData?
        ) {
            updateMetadata(nowPlayData, playBackTimeData)
        }

        override fun onNowPlayingItemChangeEvent(nowPlayData: NowPlayData) {
            updateMetadata(nowPlayData, null)
        }

        override fun onNowPlayingStatusChangeEvent(nowPlayingStatusData: NowPlayingStatusData) {
            // TODO fav and lib
        }

        override fun onShuffleModeChangeEvent(isShuffle: Boolean) {
            // TODO shuffle mode change event
        }
    }

    fun connect(baseUrl: String, token: String) {
        ciderSocketModel = get<CiderSocketModel> { parametersOf(baseUrl) }
        ciderRPCModel = get<CiderRPCModel> { parametersOf(baseUrl, token) }

        ciderSocketModel?.connect(
            connectionEventListener = socketConnectionEventListener,
            playBackEventListener = playBackEventListener
        )
    }

    private fun initMetadata() {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val data = ciderRPCModel?.getNowPlay()
                updateMetadata(data?.first, data?.second)
            } catch (e: Exception) {
                Log.e("RemoteMediaViewModel", "Error initializing metadata: ${e.message}")
                updateMetadata(null, null)
            }
        }
    }

    private fun updateMetadata(
        nowPlayData: NowPlayData?,
        playBackTimeData: PlayBackTimeData?,
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            mutex.withLock {
                if (nowPlayData == null) {
                    currentMetadata = MediaMetadata.Builder()
                        .putText(MediaMetadata.METADATA_KEY_TITLE, "No song playing")
                        .build()
                    mediaSession.setMetadata(currentMetadata)
                    callback.onMetadataUpdated()
                } else {
                    currentMetadata = MediaMetadata.Builder()
                        .putText(MediaMetadata.METADATA_KEY_TITLE, nowPlayData.name)
                        .putText(MediaMetadata.METADATA_KEY_ARTIST, nowPlayData.artistName)
                        .putText(MediaMetadata.METADATA_KEY_ALBUM, nowPlayData.albumName)
                        .putLong(
                            MediaMetadata.METADATA_KEY_DURATION,
                            playBackTimeData?.duration?.toLong()?.times(1000L) ?: 0L
                        )
                        .build()
                    mediaSession.setMetadata(currentMetadata)
                    callback.onMetadataUpdated()
                    getBitmapFromUrl(nowPlayData.artwork)
                }
            }
            updatePlaybackState(playBackTimeData)
            Log.d("RemoteMediaViewModel", "Metadata updated: $currentMetadata")
        }
    }

    private fun updatePlaybackState(
        playBackTimeData: PlayBackTimeData?,
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            mutex.withLock {
                if (playBackTimeData != null) {
                    if (currentMetadata.getLong(MediaMetadata.METADATA_KEY_DURATION) == 0L) { // not set duration
                        currentMetadata = MediaMetadata.Builder(currentMetadata)
                            .putLong(
                                MediaMetadata.METADATA_KEY_DURATION,
                                playBackTimeData.duration.toLong() * 1000L
                            )
                            .build()
                        mediaSession.setMetadata(currentMetadata)
                        callback.onMetadataUpdated()
                    }
                    val currentState = currentPlaybackState.state
                    currentPlaybackState = PlaybackState.Builder()
                        .setState(
                            if (playBackTimeData.isPlaying) {
                                PlaybackState.STATE_PLAYING
                            } else {
                                PlaybackState.STATE_PAUSED
                            },
                            playBackTimeData.currentTime.toLong().times(1000L),
                            0f
                        )
                        .build()
                    mediaSession.setPlaybackState(currentPlaybackState)
                    if (currentState != currentPlaybackState.state) {
                        callback.onMetadataUpdated()
                    }
                } else {
                    currentPlaybackState = PlaybackState.Builder()
                        .setState(PlaybackState.STATE_NONE, 0L, 0f)
                        .build()
                    mediaSession.setPlaybackState(currentPlaybackState)
                    callback.onMetadataUpdated()
                }
            }
        }
    }

    private suspend fun getBitmapFromUrl(url: String?) {
        val loader = ImageLoader(context)
        val request = cacheableImageRequest(
            context = context,
            data = url
        ).allowHardware(false).build()
        val result = loader.execute(request)
        if (result is SuccessResult) {
            result.image.toBitmap()
            currentMetadata = MediaMetadata.Builder(currentMetadata)
                .putBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART, result.image.toBitmap())
                .build()
            mediaSession.setMetadata(currentMetadata)
            callback.onMetadataUpdated()
        } else if (result is ErrorResult) {
            Log.d("RemoteMediaViewModel", "Error loading image: ${result.throwable.message}")
        }
    }

    fun playPause() = coroutineScope.launch {
        try {
            ciderRPCModel?.playPause()
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("RemoteMediaViewModel", th.toString())
        }
    }

    fun next() = coroutineScope.launch {
        try {
            ciderRPCModel?.next()
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("RemoteMediaViewModel", th.toString())
        }
    }

    fun prev() = coroutineScope.launch {
        try {
            ciderRPCModel?.prev()
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("RemoteMediaViewModel", th.toString())
        }
    }

    interface Callback {
        fun onMetadataUpdated()
        fun onFinish()
    }
}
