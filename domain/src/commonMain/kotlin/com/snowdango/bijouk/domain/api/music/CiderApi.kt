package com.snowdango.bijouk.domain.api.music

import com.snowdango.bijouk.domain.api.entity.AlbumData
import com.snowdango.bijouk.domain.api.entity.ArtistsData
import com.snowdango.bijouk.domain.api.entity.PlaylistData
import com.snowdango.bijouk.domain.api.entity.SongData
import com.snowdango.bijouk.domain.api.getCiderHttpClient
import com.snowdango.bijouk.domain.api.request.InLibrarySearchRequestBody
import com.snowdango.bijouk.domain.api.request.LibraryRequestBody
import com.snowdango.bijouk.domain.api.request.SearchPlaylistRequestBody
import com.snowdango.bijouk.domain.api.request.SearchRequestBody
import com.snowdango.bijouk.domain.api.request.artist.ArtistDetailsRequestBody
import com.snowdango.bijouk.domain.api.request.artist.ArtistViewsRequestBody
import com.snowdango.bijouk.domain.api.response.ArtistFullAlbumResponse
import com.snowdango.bijouk.domain.api.response.ArtistSingleResponse
import com.snowdango.bijouk.domain.api.response.ArtistsResponse
import com.snowdango.bijouk.domain.api.response.ArtistsTopSongResponse
import com.snowdango.bijouk.domain.api.response.LibraryResponse
import com.snowdango.bijouk.domain.api.response.SearchInLibraryResponse
import com.snowdango.bijouk.domain.api.response.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
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

    suspend fun searchAll(query: String): SearchResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(SearchRequestBody.Companion.create(search = query))
        }
        return response.body<SearchResponse>()
    }

    suspend fun searchSongs(query: String, offset: Int, limit: Int): SearchResponse {
        val response = client.post {
            url("/api/v1/amapi/run-v3")
            contentType(ContentType.Application.Json)
            setBody(
                SearchRequestBody.Companion.create(
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
                SearchRequestBody.Companion.create(
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
                SearchRequestBody.Companion.create(
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
                SearchPlaylistRequestBody.Companion.create(
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
                InLibrarySearchRequestBody.Companion.create(
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
                InLibrarySearchRequestBody.Companion.create(
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
                InLibrarySearchRequestBody.Companion.create(
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
                InLibrarySearchRequestBody.Companion.create(
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
                LibraryRequestBody.Companion.create(
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
                LibraryRequestBody.Companion.create(
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
                LibraryRequestBody.Companion.create(
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
                LibraryRequestBody.Companion.create(
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
                ArtistDetailsRequestBody.Companion.create(
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
                ArtistViewsRequestBody.Companion.create(
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
                ArtistViewsRequestBody.Companion.create(
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
                ArtistViewsRequestBody.Companion.create(
                    artistId = artistId,
                    viewType = ArtistViewsRequestBody.ViewType.Singles,
                    limit = limit,
                    offset = offset,
                )
            )
        }
        return response.body<ArtistSingleResponse>()
    }
}