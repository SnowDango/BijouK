package com.snowdango.bijouk.domain.api

import com.snowdango.bijouk.domain.api.entity.AlbumData
import com.snowdango.bijouk.domain.api.entity.ArtistsData
import com.snowdango.bijouk.domain.api.entity.PlaylistData
import com.snowdango.bijouk.domain.api.entity.SongData
import com.snowdango.bijouk.domain.api.request.ChangeQueueIndexRequestBody
import com.snowdango.bijouk.domain.api.request.InLibrarySearchRequestBody
import com.snowdango.bijouk.domain.api.request.LibraryRequestBody
import com.snowdango.bijouk.domain.api.request.MoveQueueRequestBody
import com.snowdango.bijouk.domain.api.request.MoveSeekRequestBody
import com.snowdango.bijouk.domain.api.request.PlayRequestBody
import com.snowdango.bijouk.domain.api.request.SearchPlaylistRequestBody
import com.snowdango.bijouk.domain.api.request.SearchRequestBody
import com.snowdango.bijouk.domain.api.request.artist.ArtistDetailsRequestBody
import com.snowdango.bijouk.domain.api.request.artist.ArtistViewsRequestBody
import com.snowdango.bijouk.domain.api.response.ArtistFullAlbumResponse
import com.snowdango.bijouk.domain.api.response.ArtistSingleResponse
import com.snowdango.bijouk.domain.api.response.ArtistsResponse
import com.snowdango.bijouk.domain.api.response.ArtistsTopSongResponse
import com.snowdango.bijouk.domain.api.response.BasicResponse
import com.snowdango.bijouk.domain.api.response.LibraryResponse
import com.snowdango.bijouk.domain.api.response.NowPlayingResponse
import com.snowdango.bijouk.domain.api.response.SearchInLibraryResponse
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

    suspend fun playlistPlayById(playlistId: String): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/play-item")
            contentType(ContentType.Application.Json)
            setBody(PlayRequestBody.create(PlayRequestBody.PlayType.Playlists, playlistId))
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

    suspend fun playlistPlayNextById(playlistId: String): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/play-next")
            contentType(ContentType.Application.Json)
            setBody(PlayRequestBody.create(PlayRequestBody.PlayType.Playlists, playlistId))
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

    suspend fun playlistPlayLaterById(playlistId: String): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/play-later")
            contentType(ContentType.Application.Json)
            setBody(PlayRequestBody.create(PlayRequestBody.PlayType.Playlists, playlistId))
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

    suspend fun searchPlaylists(query: String, offset: Int, limit: Int): SearchResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                SearchPlaylistRequestBody.create(
                    search = query,
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<SearchResponse>()
    }

    suspend fun searchInLibrarySongs(
        query: String,
        offset: Int,
        limit: Int
    ): SearchInLibraryResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                InLibrarySearchRequestBody.create(
                    search = query,
                    searchTypes = listOf(InLibrarySearchRequestBody.InLibrarySearchType.Songs),
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<SearchInLibraryResponse>()
    }

    suspend fun searchInLibraryAlbums(
        query: String,
        offset: Int,
        limit: Int
    ): SearchInLibraryResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                InLibrarySearchRequestBody.create(
                    search = query,
                    searchTypes = listOf(InLibrarySearchRequestBody.InLibrarySearchType.Albums),
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<SearchInLibraryResponse>()
    }

    suspend fun searchInLibraryArtists(
        query: String,
        offset: Int,
        limit: Int
    ): SearchInLibraryResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                InLibrarySearchRequestBody.create(
                    search = query,
                    searchTypes = listOf(InLibrarySearchRequestBody.InLibrarySearchType.Artists),
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<SearchInLibraryResponse>()
    }

    suspend fun searchInLibraryPlaylists(
        query: String,
        offset: Int,
        limit: Int
    ): SearchInLibraryResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                InLibrarySearchRequestBody.create(
                    search = query,
                    searchTypes = listOf(InLibrarySearchRequestBody.InLibrarySearchType.Playlists),
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<SearchInLibraryResponse>()
    }

    suspend fun libraryAllSongs(
        limit: Int,
        offset: Int
    ): LibraryResponse<SongData> {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                LibraryRequestBody.create(
                    type = LibraryRequestBody.LibraryType.SONGS,
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<LibraryResponse<SongData>>()
    }

    suspend fun libraryAllAlbums(
        limit: Int,
        offset: Int
    ): LibraryResponse<AlbumData> {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                LibraryRequestBody.create(
                    type = LibraryRequestBody.LibraryType.ALBUMS,
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<LibraryResponse<AlbumData>>()
    }

    suspend fun libraryAllArtists(
        limit: Int,
        offset: Int
    ): LibraryResponse<ArtistsData> {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                LibraryRequestBody.create(
                    type = LibraryRequestBody.LibraryType.ARTISTS,
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<LibraryResponse<ArtistsData>>()
    }

    suspend fun libraryAllPlaylists(
        limit: Int,
        offset: Int
    ): LibraryResponse<PlaylistData> {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                LibraryRequestBody.create(
                    type = LibraryRequestBody.LibraryType.PLAYLISTS,
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<LibraryResponse<PlaylistData>>()
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
                ArtistViewsRequestBody.create(
                    artistId = artistId,
                    viewType = ArtistViewsRequestBody.ViewType.TopSongs,
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<ArtistsTopSongResponse>()
    }

    suspend fun getArtistFullAlbums(
        artistId: String,
        limit: Int,
        offset: Int
    ): ArtistFullAlbumResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                ArtistViewsRequestBody.create(
                    artistId = artistId,
                    viewType = ArtistViewsRequestBody.ViewType.FullAlbums,
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<ArtistFullAlbumResponse>()
    }

    suspend fun getArtistSingles(
        artistId: String,
        limit: Int,
        offset: Int
    ): ArtistSingleResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                ArtistViewsRequestBody.create(
                    artistId = artistId,
                    viewType = ArtistViewsRequestBody.ViewType.Singles,
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<ArtistSingleResponse>()
    }

    suspend fun stationPlayById(stationId: String): BasicResponse {
        val response = client.post {
            url("/api/v1/playback/play-item")
            contentType(ContentType.Application.Json)
            setBody(PlayRequestBody.create(PlayRequestBody.PlayType.Stations, stationId))
        }
        return response.body<BasicResponse>()
    }
}
