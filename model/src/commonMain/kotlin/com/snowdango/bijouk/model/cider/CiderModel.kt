package com.snowdango.bijouk.model.cider

import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.model.cider.data.QueueDataList
import com.snowdango.bijouk.model.cider.data.SearchData
import com.snowdango.bijouk.model.cider.mapper.convert
import com.snowdango.bijouk.repository.cider.CiderRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class CiderModel(
    baseUrl: String,
    token: String,
) : KoinComponent {

    private val repository: CiderRepository by inject<CiderRepository> {
        parametersOf(
            baseUrl,
            token
        )
    }

    suspend fun isActive(): Boolean {
        return try {
            val response = repository.getActive()
            response.status == "ok"
        } catch (_: Throwable) {
            false
        }
    }

    suspend fun getNowPlay(): Triple<NowPlayData, PlayBackTimeData, NowPlayingStatusData> {
        val nowPlay = repository.getNowPlay()
        return nowPlay.convert()
    }

    suspend fun playPause() {
        repository.postPlayPause()
    }

    suspend fun next() {
        repository.postNext()
    }

    suspend fun prev() {
        repository.postPrev()
    }

    suspend fun seekTo(to: Float) {
        repository.seekTo(to)
    }

    suspend fun moveQueue(index: Int, moveIndex: Int) {
        repository.postMoveQueue(index, moveIndex)
    }

    suspend fun getQueue(): QueueDataList {
        val queues = repository.getQueue()
        val currentIndex = queues.indexOfLast { it.attributes.currentPlaybackTime != null }
        return QueueDataList(
            list = queues.mapIndexed { index, data -> data.convert(index, currentIndex) },
        )
    }

    suspend fun searchAll(query: String): SearchData {
        val result = repository.searchAll(query)
        return result.convert()
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
    }

    interface SocketConnectionEventListener {
        fun onConnect()
        fun onDisConnect()
    }
}
