package com.snowdango.bijouk.domain.api

import com.snowdango.bijouk.domain.api.response.BasicResponse
import com.snowdango.bijouk.domain.api.response.NowPlayingResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CiderApi(
    private val baseUrl: String,
    private val token: String,
) {

    private val client: HttpClient by lazy {
        HttpClient(Android) {
            install(DefaultRequest) {
                url(baseUrl)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("apptoken", token)
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.BODY
            }
            expectSuccess = false
        }
    }

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

    suspend fun playPause(): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/playpause")
            header("content-type", "application/json")
            setBody("{}")
        }
        return response.body<BasicResponse>()
    }

    suspend fun next(): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/next")
            header("content-type", "application/json")
            setBody("{}")
        }
        return response.body<BasicResponse>()
    }

    suspend fun previous(): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/previous")
            header("content-type", "application/json")
            setBody("{}")
        }
        return response.body<BasicResponse>()
    }
}
