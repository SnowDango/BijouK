package com.snowdango.bijouk.repository.cider


import com.snowdango.bijouk.domain.api.entity.music.catalog.Albums
import com.snowdango.bijouk.domain.api.entity.music.catalog.Songs
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryAlbums
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryArtists
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryPlaylistFolders
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryPlaylists
import com.snowdango.bijouk.domain.api.entity.music.library.LibrarySongs
import com.snowdango.bijouk.domain.api.music.CiderApi
import com.snowdango.bijouk.domain.api.music.response.ArtistsResponse
import com.snowdango.bijouk.domain.api.music.response.LibraryResponse
import com.snowdango.bijouk.domain.api.music.response.LibrarySearchResponse
import com.snowdango.bijouk.domain.api.music.response.RelationshipViewResponse
import com.snowdango.bijouk.domain.api.music.response.SearchResponse

class CiderRepository(
    private val ciderApi: CiderApi,
) {

    suspend fun searchAll(query: String): SearchResponse {
        return ciderApi.searchAll(query)
    }

    suspend fun searchSongs(query: String, offset: Int, limit: Int): SearchResponse {
        return ciderApi.searchSongs(query, offset, limit)
    }

    suspend fun searchAlbums(query: String, offset: Int, limit: Int): SearchResponse {
        return ciderApi.searchAlbums(query, offset, limit)
    }

    suspend fun searchArtists(query: String, offset: Int, limit: Int): SearchResponse {
        return ciderApi.searchArtists(query, offset, limit)
    }

    suspend fun searchPlaylists(query: String, offset: Int, limit: Int): SearchResponse {
        return ciderApi.searchPlaylists(query, offset, limit)
    }

    suspend fun searchInLibrarySongs(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
        return ciderApi.searchInLibrarySongs(query, offset, limit)
    }

    suspend fun searchInLibraryAlbums(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
        return ciderApi.searchInLibraryAlbums(query, offset, limit)
    }

    suspend fun searchInLibraryArtists(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
        return ciderApi.searchInLibraryArtists(query, offset, limit)
    }

    suspend fun searchInLibraryPlaylists(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
        return ciderApi.searchInLibraryPlaylists(query, offset, limit)
    }

    suspend fun libraryAllSongs(
        limit: Int,
        offset: Int
    ): LibraryResponse<LibrarySongs> {
        return ciderApi.libraryAllSongs(limit, offset)
    }

    suspend fun libraryAllAlbums(
        limit: Int,
        offset: Int
    ): LibraryResponse<LibraryAlbums> {
        return ciderApi.libraryAllAlbums(limit, offset)
    }

    suspend fun libraryAllArtists(
        limit: Int,
        offset: Int
    ): LibraryResponse<LibraryArtists> {
        return ciderApi.libraryAllArtists(limit, offset)
    }

    suspend fun libraryAllPlaylists(
        limit: Int,
        offset: Int
    ): LibraryResponse<LibraryPlaylists> {
        return ciderApi.libraryAllPlaylists(limit, offset)
    }

    suspend fun libraryAllPlaylistFolders(
        limit: Int,
        offset: Int
    ): LibraryResponse<LibraryPlaylistFolders> {
        return ciderApi.libraryAllPlaylistFolders(limit, offset)
    }

    suspend fun libraryPlaylistFolderChildren(
        folderId: String,
        limit: Int,
        offset: Int
    ): LibraryResponse<LibraryPlaylists> {
        return ciderApi.libraryPlaylistFolderChildren(folderId, limit, offset)
    }

    suspend fun getArtistDetails(artistId: String): ArtistsResponse {
        return ciderApi.getArtistDetails(artistId)
    }

    suspend fun getArtistTopSongs(
        artistId: String,
        limit: Int,
        offset: Int
    ): RelationshipViewResponse<Songs> {
        return ciderApi.getArtistTopSongs(artistId, limit, offset)
    }

    suspend fun getArtistFullAlbums(
        artistId: String,
        limit: Int,
        offset: Int,
    ): RelationshipViewResponse<Albums> {
        return ciderApi.getArtistFullAlbums(artistId, limit, offset)
    }

    suspend fun getArtistSingles(
        artistId: String,
        limit: Int,
        offset: Int,
    ): RelationshipViewResponse<Albums> {
        return ciderApi.getArtistSingles(artistId, limit, offset)
    }
}
