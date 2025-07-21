package com.snowdango.bijouk.domain.api

import com.snowdango.bijouk.domain.api.request.ArtistDetailsRequestBody
import com.snowdango.bijouk.domain.api.request.ArtistsViewsRequestBody
import com.snowdango.bijouk.domain.api.request.ChangeQueueIndexRequestBody
import com.snowdango.bijouk.domain.api.request.InLibrarySearchRequestBody
import com.snowdango.bijouk.domain.api.request.MoveQueueRequestBody
import com.snowdango.bijouk.domain.api.request.MoveSeekRequestBody
import com.snowdango.bijouk.domain.api.request.PlayRequestBody
import com.snowdango.bijouk.domain.api.request.SearchRequestBody
import com.snowdango.bijouk.domain.api.response.ArtistsResponse
import com.snowdango.bijouk.domain.api.response.ArtistsTopSongResponse
import com.snowdango.bijouk.domain.api.response.BasicResponse
import com.snowdango.bijouk.domain.api.response.NowPlayingResponse
import com.snowdango.bijouk.domain.api.response.SearchResponse
import com.snowdango.bijouk.domain.api.response.data.QueueResponseData
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

    suspend fun songPlayById(songId: String): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/play-item")
            contentType(ContentType.Application.Json)
            setBody(PlayRequestBody.create(PlayRequestBody.PlayType.Songs, songId))
        }
        return response.body<BasicResponse>()
    }

    suspend fun albumPlayById(albumId: String): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/play-item")
            contentType(ContentType.Application.Json)
            setBody(PlayRequestBody.create(PlayRequestBody.PlayType.Albums, albumId))
        }
        return response.body<BasicResponse>()
    }

    suspend fun songPlayNextById(songId: String): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/play-next")
            contentType(ContentType.Application.Json)
            setBody(PlayRequestBody.create(PlayRequestBody.PlayType.Songs, songId))
        }
        return response.body<BasicResponse>()
    }

    suspend fun albumPlayNextById(albumId: String): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/play-next")
            contentType(ContentType.Application.Json)
            setBody(PlayRequestBody.create(PlayRequestBody.PlayType.Albums, albumId))
        }
        return response.body<BasicResponse>()
    }

    suspend fun songPlayLaterById(songId: String): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/play-later")
            contentType(ContentType.Application.Json)
            setBody(PlayRequestBody.create(PlayRequestBody.PlayType.Songs, songId))
        }
        return response.body<BasicResponse>()
    }

    suspend fun albumPlayLaterById(albumId: String): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/play-later")
            contentType(ContentType.Application.Json)
            setBody(PlayRequestBody.create(PlayRequestBody.PlayType.Albums, albumId))
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

    suspend fun searchSongs(query: String, offset: Int, limit: Int): SearchResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                SearchRequestBody.create(
                    search = query,
                    searchTypes = listOf(SearchRequestBody.SearchType.Songs),
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<SearchResponse>()
    }

    suspend fun searchAlbums(query: String, offset: Int, limit: Int): SearchResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                SearchRequestBody.create(
                    search = query,
                    searchTypes = listOf(SearchRequestBody.SearchType.Albums),
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<SearchResponse>()
    }

    suspend fun searchArtists(query: String, offset: Int, limit: Int): SearchResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                SearchRequestBody.create(
                    search = query,
                    searchTypes = listOf(SearchRequestBody.SearchType.Artists),
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<SearchResponse>()
    }

    suspend fun getArtistDetails(artistId: String): ArtistsResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                ArtistDetailsRequestBody.create(
                    artistId,
                )
            )
        }
        return response.body<ArtistsResponse>()
    }

    suspend fun getArtistTopSongs(
        artistId: String,
        limit: Int,
        offset: Int
    ): ArtistsTopSongResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                ArtistsViewsRequestBody.create(
                    artistId = artistId,
                    viewType = ArtistsViewsRequestBody.ViewType.TopSongs,
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<ArtistsTopSongResponse>()
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
