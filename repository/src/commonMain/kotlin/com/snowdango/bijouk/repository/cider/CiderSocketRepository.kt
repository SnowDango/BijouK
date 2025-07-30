package com.snowdango.bijouk.repository.cider

import com.snowdango.bijouk.domain.api.socket.CiderSocket
import com.snowdango.bijouk.domain.api.socket.event.NowPlayingItemDidChangeEvent
import com.snowdango.bijouk.domain.api.socket.event.NowPlayingStatusDidChange
import com.snowdango.bijouk.domain.api.socket.event.PlayBackStateDidChangeEvent
import com.snowdango.bijouk.domain.api.socket.event.PlayBackTimeDidChangeEvent

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