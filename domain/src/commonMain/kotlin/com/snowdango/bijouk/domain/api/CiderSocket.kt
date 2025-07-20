package com.snowdango.bijouk.domain.api

import com.piasy.kmp.socketio.socketio.IO
import com.piasy.kmp.socketio.socketio.Socket
import com.snowdango.bijouk.domain.Logger
import com.snowdango.bijouk.domain.api.event.NowPlayingItemDidChangeEvent
import com.snowdango.bijouk.domain.api.event.NowPlayingStatusDidChange
import com.snowdango.bijouk.domain.api.event.PlayBackStateDidChangeEvent
import com.snowdango.bijouk.domain.api.event.PlayBackTimeDidChangeEvent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject


class CiderSocket(
    private val baseUrl: String,
) {

    private val logger: Logger = Logger("CiderSocket")
    private var client: Socket? = null

    fun startSocket(
        onConnect: () -> Unit,
        onDisConnect: () -> Unit,
        onTimeChangeEvent: (PlayBackTimeDidChangeEvent) -> Unit,
        onStateChangeEvent: (PlayBackStateDidChangeEvent) -> Unit,
        onNowPlayingItemChangeEvent: (NowPlayingItemDidChangeEvent) -> Unit,
        onNowPlayingStatusChangeEvent: (NowPlayingStatusDidChange) -> Unit
    ) {
        logger.d(null, "startSocket")
        IO.socket(
            uri = baseUrl,
            opt = IO.Options().apply {
                forceNew = true
                reconnection = true
                timeout = 5000
            }
        ) { socket ->
            socket.on("API:Playback") { param ->
                val json = Json.decodeFromString(JsonObject.serializer(), param[0].toString())
                logger.d(null, json.toString())
                val type =
                    EventType.entries.find {
                        it.type == json["type"].toString().removeSurrounding("\"")
                    }
                when (type) {
                    EventType.PlayBackTimeDidChange -> { // time change
                        val data =
                            Json.decodeFromString<PlayBackTimeDidChangeEvent>(
                                param[0].toString()
                            )
                        logger.d(null, data.toString())
                        onTimeChangeEvent.invoke(data)
                    }

                    EventType.PlayBackStateDidChange -> { // song change
                        val data =
                            Json.decodeFromString<PlayBackStateDidChangeEvent>(
                                param[0].toString()
                            )
                        logger.d(null, data.toString())
                        onStateChangeEvent.invoke(data)
                    }

                    EventType.NowPlayingItemDidChange -> { // fav and lib state change
                        val data =
                            Json.decodeFromString<NowPlayingItemDidChangeEvent>(
                                param[0].toString()
                            )
                        logger.d(null, data.toString())
                        onNowPlayingItemChangeEvent.invoke(data)
                    }

                    EventType.NowPlayingStatusDidChange -> { // metadata change
                        val data =
                            Json.decodeFromString<NowPlayingStatusDidChange>(
                                param[0].toString()
                            )
                        logger.d(null, data.toString())
                        onNowPlayingStatusChangeEvent.invoke(data)
                    }

                    else -> logger.w(null, "UnknownTypeError: ${Json.encodeToString(json["type"])}")
                }
            }.on("connect") {
                logger.d(null, "connection")
                onConnect.invoke()
            }.on("disconnect") {
                logger.d(null, "disconnection")
                onDisConnect.invoke()
            }
            socket.open()
        }
    }

    fun closeSocket() {
        client?.close()
    }

    enum class EventType(val type: String) {
        PlayBackTimeDidChange("playbackStatus.playbackTimeDidChange"),
        PlayBackStateDidChange("playbackStatus.playbackStateDidChange"),
        NowPlayingStatusDidChange("playbackStatus.nowPlayingStatusDidChange"),
        NowPlayingItemDidChange("playbackStatus.nowPlayingItemDidChange")
    }

    interface SocketEventListener {
        fun onTimeChangeEvent(playBackTimeDidChangeEvent: PlayBackTimeDidChangeEvent)
        fun onStateChangeEvent(playBackStateDidChangeEvent: PlayBackStateDidChangeEvent)
        fun onNowPlayingItemChangeEvent(nowPlayingItemDidChangeEvent: NowPlayingItemDidChangeEvent)
        fun onNowPlayingStatusChangeEvent(nowPlayingStatusDidChange: NowPlayingStatusDidChange)
        fun onConnect()
        fun onDisconnect()
    }
}