package com.snowdango.bijouk.domain.api.music

import com.snowdango.bijouk.domain.api.entity.music.catalog.Albums
import com.snowdango.bijouk.domain.api.entity.music.catalog.Songs
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryAlbums
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryArtists
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryPlaylists
import com.snowdango.bijouk.domain.api.entity.music.library.LibrarySongs
import com.snowdango.bijouk.domain.api.getCiderHttpClient
import com.snowdango.bijouk.domain.api.music.request.ArtistDetailsRequestBody
import com.snowdango.bijouk.domain.api.music.request.ArtistViewsRequestBody
import com.snowdango.bijouk.domain.api.music.request.InLibrarySearchRequestBody
import com.snowdango.bijouk.domain.api.music.request.LibraryRequestBody
import com.snowdango.bijouk.domain.api.music.request.SearchPlaylistRequestBody
import com.snowdango.bijouk.domain.api.music.request.SearchRequestBody
import com.snowdango.bijouk.domain.api.music.response.ArtistsResponse
import com.snowdango.bijouk.domain.api.music.response.LibraryResponse
import com.snowdango.bijouk.domain.api.music.response.LibrarySearchResponse
import com.snowdango.bijouk.domain.api.music.response.RelationshipViewResponse
import com.snowdango.bijouk.domain.api.music.response.SearchResponse
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
    ): LibrarySearchResponse {
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
        return response.body<LibrarySearchResponse>()
    }

    suspend fun searchInLibraryAlbums(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
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
        return response.body<LibrarySearchResponse>()
    }

    suspend fun searchInLibraryArtists(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
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
        return response.body<LibrarySearchResponse>()
    }

    suspend fun searchInLibraryPlaylists(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
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
        return response.body<LibrarySearchResponse>()
    }

    suspend fun libraryAllSongs(
        limit: Int,
        offset: Int
    ): LibraryResponse<LibrarySongs> {
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
        return response.body<LibraryResponse<LibrarySongs>>()
    }

    suspend fun libraryAllAlbums(
        limit: Int,
        offset: Int
    ): LibraryResponse<LibraryAlbums> {
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
        return response.body<LibraryResponse<LibraryAlbums>>()
    }

    suspend fun libraryAllArtists(
        limit: Int,
        offset: Int
    ): LibraryResponse<LibraryArtists> {
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
        return response.body<LibraryResponse<LibraryArtists>>()
    }

    suspend fun libraryAllPlaylists(
        limit: Int,
        offset: Int
    ): LibraryResponse<LibraryPlaylists> {
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
        return response.body<LibraryResponse<LibraryPlaylists>>()
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
    ): RelationshipViewResponse<Songs> {
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
        return response.body<RelationshipViewResponse<Songs>>()
    }

    suspend fun getArtistFullAlbums(
        artistId: String,
        limit: Int,
        offset: Int
    ): RelationshipViewResponse<Albums> {
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
        return response.body<RelationshipViewResponse<Albums>>()
    }

    suspend fun getArtistSingles(
        artistId: String,
        limit: Int,
        offset: Int
    ): RelationshipViewResponse<Albums> {
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
        return response.body<RelationshipViewResponse<Albums>>()
    }
}