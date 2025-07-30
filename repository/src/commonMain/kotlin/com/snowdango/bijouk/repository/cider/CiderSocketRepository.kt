package com.snowdango.bijouk.repository.cider

import com.snowdango.bijouk.domain.api.event.NowPlayingItemDidChangeEvent
import com.snowdango.bijouk.domain.api.event.NowPlayingStatusDidChange
import com.snowdango.bijouk.domain.api.event.PlayBackStateDidChangeEvent
import com.snowdango.bijouk.domain.api.event.PlayBackTimeDidChangeEvent
import com.snowdango.bijouk.domain.api.socket.CiderSocket

class CiderSocketRepository(
    private val ciderSocket: CiderSocket
) {

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