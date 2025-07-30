package com.snowdango.bijouk.domain.api.rpc

import com.snowdango.bijouk.domain.api.getCiderHttpClient
import com.snowdango.bijouk.domain.api.request.ChangeQueueIndexRequestBody
import com.snowdango.bijouk.domain.api.request.MoveQueueRequestBody
import com.snowdango.bijouk.domain.api.request.MoveSeekRequestBody
import com.snowdango.bijouk.domain.api.request.PlayRequestBody
import com.snowdango.bijouk.domain.api.response.BasicResponse
import com.snowdango.bijouk.domain.api.response.NowPlayingResponse
import com.snowdango.bijouk.domain.api.response.data.QueueResponseData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CiderRPCApi(
    private val baseUrl: String,
    private val token: String,
) {

    private val client: HttpClient by lazy { getCiderHttpClient(baseUrl, token) }

    suspend fun active(): BasicResponse {
        val response = client.get {
            url("/api/v1/playback/active")
        }
        return response.body<BasicResponse>()
    }

    suspend fun nowPlaying(): NowPlayingResponse {
        val response = client.get {
            url("/api/v1/playback/now-playing")
        }
        return response.body<NowPlayingResponse>()
    }

    suspend fun getQueue(): List<QueueResponseData> {
        val response = client.get {
            url("/api/v1/playback/queue")
        }
        return response.body<List<QueueResponseData>>()
    }

    suspend fun playPause(): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/playpause")
            contentType(ContentType.Application.Json)
            setBody("{}")
        }
        return response.body<BasicResponse>()
    }

    suspend fun next(): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/next")
            contentType(ContentType.Application.Json)
            setBody("{}")
        }
        return response.body<BasicResponse>()
    }

    suspend fun previous(): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/previous")
            contentType(ContentType.Application.Json)
            setBody("{}")
        }
        return response.body<BasicResponse>()
    }

    suspend fun seekTo(to: Float): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/seek")
            contentType(ContentType.Application.Json)
            setBody(MoveSeekRequestBody(position = to))
        }
        return response.body<BasicResponse>()
    }

    suspend fun moveQueue(index: Int, moveIndex: Int): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/queue/move-to-position")
            contentType(ContentType.Application.Json)
            setBody(MoveQueueRequestBody(startIndex = index, destinationIndex = moveIndex))
        }
        return response.body<BasicResponse>()
    }

    suspend fun changeQueueIndex(index: Int): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/queue/change-to-index")
            contentType(ContentType.Application.Json)
            setBody(ChangeQueueIndexRequestBody(index = index))
        }
        return response.body<BasicResponse>()
    }

    suspend fun playById(id: String, type: PlayRequestBody.PlayType): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/play-item")
            contentType(ContentType.Application.Json)
            setBody(PlayRequestBody.Companion.create(type, id))
        }
        return response.body<BasicResponse>()
    }

    suspend fun playNextById(id: String, type: PlayRequestBody.PlayType): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/play-next")
            contentType(ContentType.Application.Json)
            setBody(PlayRequestBody.Companion.create(type, id))
        }
        return response.body<BasicResponse>()
    }

    suspend fun playLaterById(id: String, type: PlayRequestBody.PlayType): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/play-later")
            contentType(ContentType.Application.Json)
            setBody(PlayRequestBody.Companion.create(type, id))
        }
        return response.body<BasicResponse>()
    }

}