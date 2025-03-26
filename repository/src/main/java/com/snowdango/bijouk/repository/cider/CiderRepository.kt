package com.snowdango.bijouk.repository.cider

import com.snowdango.bijouk.domain.api.CiderApi
import com.snowdango.bijouk.domain.api.CiderSocket
import com.snowdango.bijouk.domain.api.event.NowPlayingItemDidChangeEvent
import com.snowdango.bijouk.domain.api.event.NowPlayingStatusDidChange
import com.snowdango.bijouk.domain.api.event.PlayBackStateDidChangeEvent
import com.snowdango.bijouk.domain.api.event.PlayBackTimeDidChangeEvent
import com.snowdango.bijouk.domain.api.response.BasicResponse
import com.snowdango.bijouk.domain.api.response.NowPlayingResponse
import com.snowdango.bijouk.domain.api.response.data.QueueResponseData

class CiderRepository(
    private val ciderApi: CiderApi,
    private val ciderSocket: CiderSocket,
) {

    suspend fun getActive(): BasicResponse {
        return ciderApi.active()
    }

    suspend fun getNowPlay(): NowPlayingResponse {
        return ciderApi.nowPlaying()
    }

    suspend fun getQueue(): List<QueueResponseData> {
        return ciderApi.getQueue()
    }

    suspend fun postPlayPause(): BasicResponse {
        return ciderApi.playPause()
    }

    suspend fun postNext(): BasicResponse {
        return ciderApi.next()
    }

    suspend fun postPrev(): BasicResponse {
        return ciderApi.previous()
    }

    fun connect(
        onConnect: () -> Unit,
        onDisConnect: () -> Unit,
        onTimeChangeEvent: (PlayBackTimeDidChangeEvent) -> Unit,
        onStateChangeEvent: (PlayBackStateDidChangeEvent) -> Unit,
        onNowPlayingItemChangeEvent: (NowPlayingItemDidChangeEvent) -> Unit,
        onNowPlayingStatusChangeEvent: (NowPlayingStatusDidChange) -> Unit
    ) {
        ciderSocket.startSocket(
            onConnect,
            onDisConnect,
            onTimeChangeEvent,
            onStateChangeEvent,
            onNowPlayingItemChangeEvent,
            onNowPlayingStatusChangeEvent
        )
    }

    fun disconnect() {
        ciderSocket.closeSocket()
    }
}
