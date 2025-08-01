package com.snowdango.bijouk.model.cider

import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.model.cider.mapper.event.convert
import com.snowdango.bijouk.repository.cider.CiderSocketRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class CiderSocketModel(
    baseUrl: String,
) : KoinComponent {

    private val repository: CiderSocketRepository by inject<CiderSocketRepository> {
        parametersOf(baseUrl)
    }

    fun connect(
        connectionEventListener: SocketConnectionEventListener,
        playBackEventListener: PlayBackStatusEventListener,
    ) {
        repository.connect(
            onConnect = {
                connectionEventListener.onConnect()
            },
            onDisConnect = {
                connectionEventListener.onDisConnect()
            },
            onTimeChangeEvent = {
                playBackEventListener.onTimeChangeEvent(it.convert())
            },
            onStateChangeEvent = {
                val data = it.convert()
                playBackEventListener.onStateChangeEvent(data.first, data.second)
            },
            onNowPlayingItemChangeEvent = {
                playBackEventListener.onNowPlayingItemChangeEvent(it.convert())
            },
            onNowPlayingStatusChangeEvent = {
                playBackEventListener.onNowPlayingStatusChangeEvent(it.convert())
            },
            onShuffleModeChangeEvent = {
                playBackEventListener.onShuffleModeChangeEvent(it)
            }
        )
    }

    fun disconnect() {
        repository.disconnect()
    }

    interface PlayBackStatusEventListener {
        fun onTimeChangeEvent(playBackTimeData: PlayBackTimeData)
        fun onStateChangeEvent(nowPlayData: NowPlayData?, playBackTimeData: PlayBackTimeData?)
        fun onNowPlayingItemChangeEvent(nowPlayData: NowPlayData)
        fun onNowPlayingStatusChangeEvent(nowPlayingStatusData: NowPlayingStatusData)
        fun onShuffleModeChangeEvent(isShuffle: Boolean)
    }

    interface SocketConnectionEventListener {
        fun onConnect()
        fun onDisConnect()
    }

}