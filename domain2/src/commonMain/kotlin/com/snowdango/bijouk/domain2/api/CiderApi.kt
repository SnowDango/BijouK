package com.snowdango.bijouk.domain2.api

import com.snowdango.bijouk.domain2.api.request.InLibrarySearchRequestBody
import com.snowdango.bijouk.domain2.api.request.MoveQueueRequestBody
import com.snowdango.bijouk.domain2.api.request.SearchRequestBody
import com.snowdango.bijouk.domain2.api.response.BasicResponse
import com.snowdango.bijouk.domain2.api.response.NowPlayingResponse
import com.snowdango.bijouk.domain2.api.response.SearchResponse
import com.snowdango.bijouk.domain2.api.response.data.QueueResponseData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CiderApi(
    private val baseUrl: String,
    private val token: String,
) {

    private val client: HttpClient by lazy { getCiderApi(baseUrl, token) }

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

    suspend fun moveQueue(index: Int, moveIndex: Int): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/queue/move-to-position")
            contentType(ContentType.Application.Json)
            setBody(MoveQueueRequestBody(startIndex = index, destinationIndex = moveIndex))
        }
        return response.body<BasicResponse>()
    }

    suspend fun searchAll(query: String): SearchResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(SearchRequestBody.create(search = query))
        }
        return response.body<SearchResponse>()
    }

    suspend fun inLibrarySearchAll(query: String): SearchResponse {
        val response = client.get {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(InLibrarySearchRequestBody.create(search = query))
        }
        return response.body<SearchResponse>()
    }
}

expect fun getCiderApi(baseUrl: String, token: String): HttpClient
