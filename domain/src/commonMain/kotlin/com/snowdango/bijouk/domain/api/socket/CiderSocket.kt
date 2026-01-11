package com.snowdango.bijouk.domain.api.socket

import com.piasy.kmp.socketio.socketio.IO
import com.piasy.kmp.socketio.socketio.Socket
import com.snowdango.bijouk.analytics.LogUtil
import com.snowdango.bijouk.domain.api.socket.event.NowPlayingItemDidChangeEvent
import com.snowdango.bijouk.domain.api.socket.event.NowPlayingStatusDidChange
import com.snowdango.bijouk.domain.api.socket.event.PlayBackStateDidChangeEvent
import com.snowdango.bijouk.domain.api.socket.event.PlayBackTimeDidChangeEvent
import com.snowdango.bijouk.domain.api.socket.event.ShuffleModeDidChangeEvent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

class CiderSocket(
    private val baseUrl: String,
) {

    private var client: Socket? = null

    fun startSocket(
        onConnect: () -> Unit,
        onDisConnect: () -> Unit,
        onTimeChangeEvent: (PlayBackTimeDidChangeEvent) -> Unit,
        onStateChangeEvent: (PlayBackStateDidChangeEvent) -> Unit,
        onNowPlayingItemChangeEvent: (NowPlayingItemDidChangeEvent) -> Unit,
        onNowPlayingStatusChangeEvent: (NowPlayingStatusDidChange) -> Unit,
        onShuffleModeChangeEvent: (Boolean) -> Unit,
    ) {
        LogUtil.socketDebug(message = "startSocket")
        IO.socket(
            uri = baseUrl,
            opt = IO.Options().apply {
                forceNew = true
                reconnection = true
                timeout = 5000
            }
        ) { socket ->
            client = socket
            socket.on("API:Playback") { param ->
                val json = Json.Default.decodeFromString(
                    JsonObject.Companion.serializer(),
                    param[0].toString()
                )
                LogUtil.socketDebug(message = json.toString())
                val type =
                    EventType.entries.find {
                        it.type == json["type"].toString().removeSurrounding("\"")
                    }
                when (type) {
                    EventType.PlayBackTimeDidChange -> { // time change
                        val data =
                            Json.Default.decodeFromString<PlayBackTimeDidChangeEvent>(
                                param[0].toString()
                            )
                        LogUtil.socketDebug(message = data.toString())
                        onTimeChangeEvent.invoke(data)
                    }

                    EventType.PlayBackStateDidChange -> { // song change
                        val data =
                            Json.Default.decodeFromString<PlayBackStateDidChangeEvent>(
                                param[0].toString()
                            )
                        LogUtil.socketDebug(message = data.toString())
                        onStateChangeEvent.invoke(data)
                    }

                    EventType.NowPlayingItemDidChange -> { // fav and lib state change
                        LogUtil.socketDebug(message = param.toString())
                        val data =
                            Json.Default.decodeFromString<NowPlayingItemDidChangeEvent>(
                                param[0].toString()
                            )
                        LogUtil.socketDebug(message = data.toString())
                        onNowPlayingItemChangeEvent.invoke(data)
                    }

                    EventType.NowPlayingStatusDidChange -> { // metadata change
                        val data =
                            Json.Default.decodeFromString<NowPlayingStatusDidChange>(
                                param[0].toString()
                            )
                        LogUtil.socketDebug(message = data.toString())
                        onNowPlayingStatusChangeEvent.invoke(data)
                    }

                    EventType.ShuffleModeDidChange -> {
                        val data = Json.Default.decodeFromString<ShuffleModeDidChangeEvent>(
                            param[0].toString()
                        )
                        LogUtil.socketDebug(message = data.toString())
                        onShuffleModeChangeEvent.invoke(data.data == 1)
                    }

                    else -> LogUtil.w(
                        message = "UnknownTypeError: ${Json.Default.encodeToString(json["type"])}"
                    )
                }
            }.on("connect") {
                LogUtil.socketDebug(message = "connect")
                onConnect.invoke()
            }.on("disconnect") {
                LogUtil.socketDebug(message = "disconnect")
                onDisConnect.invoke()
            }
            socket.open()
        }
    }

    fun closeSocket() {
        client?.close()
        client = null
    }

    enum class EventType(val type: String) {
        PlayBackTimeDidChange("playbackStatus.playbackTimeDidChange"),
        PlayBackStateDidChange("playbackStatus.playbackStateDidChange"),
        NowPlayingStatusDidChange("playbackStatus.nowPlayingStatusDidChange"),
        NowPlayingItemDidChange("playbackStatus.nowPlayingItemDidChange"),
        ShuffleModeDidChange("playerStatus.shuffleModeDidChange"),
    }
}