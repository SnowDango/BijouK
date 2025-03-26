package com.snowdango.bijouk.domain.api

import android.util.Log
import com.snowdango.bijouk.domain.api.event.NowPlayingItemDidChangeEvent
import com.snowdango.bijouk.domain.api.event.NowPlayingStatusDidChange
import com.snowdango.bijouk.domain.api.event.PlayBackStateDidChangeEvent
import com.snowdango.bijouk.domain.api.event.PlayBackTimeDidChangeEvent
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.serialization.json.Json
import org.json.JSONObject

class CiderSocket(
    private val baseUrl: String,
) {

    private val client: Socket by lazy {
        IO.socket(baseUrl, IO.Options.builder().setTimeout(5000).build())
    }

    fun startSocket(
        onConnect: () -> Unit,
        onDisConnect: () -> Unit,
        onTimeChangeEvent: (PlayBackTimeDidChangeEvent) -> Unit,
        onStateChangeEvent: (PlayBackStateDidChangeEvent) -> Unit,
        onNowPlayingItemChangeEvent: (NowPlayingItemDidChangeEvent) -> Unit,
        onNowPlayingStatusChangeEvent: (NowPlayingStatusDidChange) -> Unit
    ) {
        client.connect().on("API:Playback") { param ->
            val type = (param[0] as JSONObject).get("type")
            when (type) {
                EventType.PlayBackTimeDidChange.type -> { // time change
                    val data = Json.decodeFromString<PlayBackTimeDidChangeEvent>(param[0].toString())
                    Log.d("SocketEvent", data.toString())
                    onTimeChangeEvent.invoke(data)
                }
                EventType.PlayBackStateDidChange.type -> { // song change
                    val data = Json.decodeFromString<PlayBackStateDidChangeEvent>(param[0].toString())
                    Log.d("SocketEvent", data.toString())
                    onStateChangeEvent.invoke(data)
                }
                EventType.NowPlayingItemDidChange.type -> { // fav and lib state change
                    val data = Json.decodeFromString<NowPlayingItemDidChangeEvent>(param[0].toString())
                    Log.d("SocketEvent", data.toString())
                    onNowPlayingItemChangeEvent.invoke(data)
                }
                EventType.NowPlayingStatusDidChange.type -> { // metadata change
                    val data = Json.decodeFromString<NowPlayingStatusDidChange>(param[0].toString())
                    Log.d("SocketEvent", data.toString())
                    onNowPlayingStatusChangeEvent.invoke(data)
                }
                else -> Log.w("CiderSocket", "UnknownTypeError: $type")
            }
        }
        client.on("connect") {
            Log.d("CiderSocket", "connection")
            onConnect.invoke()
        }
        client.on("disconnect") {
            Log.d("CiderSocket", "disconnection")
            onDisConnect.invoke()
        }
    }

    fun closeSocket() {
        client.disconnect()
    }

    enum class EventType(val type: String) {
        PlayBackTimeDidChange("playbackStatus.playbackTimeDidChange"),
        PlayBackStateDidChange("playbackStatus.playbackStateDidChange"),
        NowPlayingStatusDidChange("playbackStatus.nowPlayingStatusDidChange"),
        NowPlayingItemDidChange("playbackStatus.nowPlayingItemDidChange")
    }
}
