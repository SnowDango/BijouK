package com.snowdango.bijouk.repository.cider


import com.snowdango.bijouk.api.CiderMusicApi
import com.snowdango.bijouk.api.model.AlbumsResponse
import com.snowdango.bijouk.api.model.ApiV1AmapiRunV3PostRequest
import com.snowdango.bijouk.api.model.ArtistsResponse
import com.snowdango.bijouk.api.model.LibraryAlbumsResponse
import com.snowdango.bijouk.api.model.LibraryArtistsResponse
import com.snowdango.bijouk.api.model.LibraryPlaylistFoldersResponse
import com.snowdango.bijouk.api.model.LibraryPlaylistsResponse
import com.snowdango.bijouk.api.model.LibrarySearchResponse
import com.snowdango.bijouk.api.model.LibrarySongsResponse
import com.snowdango.bijouk.api.model.PlaylistsResponse
import com.snowdango.bijouk.api.model.RelationshipViewAlbumsResponse
import com.snowdango.bijouk.api.model.RelationshipViewLibrarySongsResponse
import com.snowdango.bijouk.api.model.RelationshipViewSongsResponse
import com.snowdango.bijouk.api.model.SearchResponse
import com.snowdango.bijouk.api.model.SongsResponse
import net.thauvin.erik.urlencoder.UrlEncoderUtil

class CiderRepository(
    private val ciderMusicApi: CiderMusicApi
) {

    suspend fun searchAll(query: String): SearchResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${SearchType.entries.joinToString(",") { it.type }}" +
                        "&limit=$DEFAULT_LIMIT" +
                        "&offset=$DEFAULT_OFFSET"
            )
        ).body().actualInstance as SearchResponse
    }

    suspend fun searchSongs(query: String, offset: Int, limit: Int): SearchResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${SearchType.Songs.type}" +
                        "&limit=${limit}" +
                        "&offset=${offset}"
            )
        ).body().actualInstance as SearchResponse
    }

    suspend fun searchAlbums(query: String, offset: Int, limit: Int): SearchResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${SearchType.Albums.type}" +
                        "&limit=${limit}" +
                        "&offset=${offset}"
            )
        ).body().actualInstance as SearchResponse
    }

    suspend fun searchArtists(query: String, offset: Int, limit: Int): SearchResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${SearchType.Artists.type}" +
                        "&limit=${limit}" +
                        "&offset=${offset}"
            )
        ).body().actualInstance as SearchResponse
    }

    suspend fun searchPlaylists(query: String, offset: Int, limit: Int): SearchResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/social/jp/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=playlists" +
                        "&limit=$limit" +
                        "&offset=$offset"
            )
        ).body().actualInstance as SearchResponse
    }

    suspend fun searchInLibrarySongs(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${InLibrarySearchType.Songs.type}" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).body().actualInstance as LibrarySearchResponse
    }

    suspend fun searchInLibraryAlbums(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${InLibrarySearchType.Albums.type}" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).body().actualInstance as LibrarySearchResponse
    }

    suspend fun searchInLibraryArtists(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${InLibrarySearchType.Artists.type}" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).body().actualInstance as LibrarySearchResponse
    }

    suspend fun searchInLibraryPlaylists(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${InLibrarySearchType.Playlists.type}" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).body().actualInstance as LibrarySearchResponse
    }

    suspend fun libraryAllSongs(
        limit: Int,
        offset: Int
    ): LibrarySongsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/${SearchType.Songs.type}?" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).body().actualInstance as LibrarySongsResponse
    }

    suspend fun libraryAllAlbums(
        limit: Int,
        offset: Int
    ): LibraryAlbumsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/${SearchType.Albums.type}?" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).body().actualInstance as LibraryAlbumsResponse
    }

    suspend fun libraryAllArtists(
        limit: Int,
        offset: Int
    ): LibraryArtistsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/${SearchType.Albums.type}?" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).body().actualInstance as LibraryArtistsResponse
    }

    suspend fun libraryAllPlaylists(
        limit: Int,
        offset: Int
    ): LibraryPlaylistsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/${SearchType.Playlists.type}?" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).body().actualInstance as LibraryPlaylistsResponse
    }

    suspend fun libraryAllPlaylistFolders(
        limit: Int,
        offset: Int
    ): LibraryPlaylistFoldersResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/${SearchType.PlaylistFolders.type}?" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).body().actualInstance as LibraryPlaylistFoldersResponse
    }

    suspend fun libraryPlaylistFolderChildren(
        folderId: String,
        limit: Int,
        offset: Int
    ): LibraryPlaylistsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/playlist-folders/${folderId}/children?" +
                        "limit=$limit" +
                        "&offset=$offset"
            )
        ).body().actualInstance as LibraryPlaylistsResponse
    }

    suspend fun getAlbumDetails(albumId: String): AlbumsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/albums/$albumId"
            )
        ).body().actualInstance as AlbumsResponse
    }

    suspend fun getLibraryAlbumDetails(albumId: String): LibraryAlbumsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/albums//$albumId"
            )
        ).body().actualInstance as LibraryAlbumsResponse
    }

    suspend fun getArtistDetails(artistId: String): ArtistsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/artists/$artistId?" +
                        "include=default-playable-content"
            )
        ).body().actualInstance as ArtistsResponse
    }

    suspend fun getLibraryArtistDetails(artistId: String): ArtistsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/artists/$artistId?" +
                        "include=default-playable-content"
            )
        ).body().actualInstance as ArtistsResponse
    }

    suspend fun getPlaylistDetails(
        playlistId: String
    ): PlaylistsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/playlists/$playlistId"
            )
        ).body().actualInstance as PlaylistsResponse
    }

    suspend fun getLibraryPlaylistDetails(
        playlistId: String
    ): PlaylistsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/playlists/$playlistId"
            )
        ).body().actualInstance as PlaylistsResponse
    }

    suspend fun getPlaylistTracks(
        playlistId: String,
        limit: Int,
        offset: Int = 0,
    ): SongsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/playlists/$playlistId/tracks?" +
                        "&limit=$limit" +
                        "&offset=$offset"
            )
        ).body().actualInstance as SongsResponse
    }

    suspend fun getLibraryPlaylistTracks(
        playlistId: String,
        limit: Int,
        offset: Int = 0,
    ): LibrarySongsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/playlists/$playlistId/tracks?" +
                        "&limit=$limit" +
                        "&offset=$offset"
            )
        ).body().actualInstance as LibrarySongsResponse
    }

    suspend fun getAlbumTracks(
        albumId: String,
        limit: Int,
        offset: Int = 0,
    ): RelationshipViewSongsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/albums/${albumId}/tracks?" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        ).body().actualInstance as RelationshipViewSongsResponse
    }

    suspend fun getLibraryAlbumTracks(
        albumId: String,
        limit: Int,
        offset: Int = 0,
    ): RelationshipViewLibrarySongsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/albums/${albumId}/tracks" +
                        "?include=catalog" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        ).body().actualInstance as RelationshipViewLibrarySongsResponse
    }

    suspend fun getArtistTopSongs(
        artistId: String,
        limit: Int,
        offset: Int
    ): RelationshipViewSongsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/artists/${artistId}/view/top-songs?" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        ).body().actualInstance as RelationshipViewSongsResponse
    }

    suspend fun getArtistFullAlbums(
        artistId: String,
        limit: Int,
        offset: Int,
    ): RelationshipViewAlbumsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/artists/${artistId}/view/full-albums?" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        ).body().actualInstance as RelationshipViewAlbumsResponse
    }

    suspend fun getArtistSingles(
        artistId: String,
        limit: Int,
        offset: Int,
    ): RelationshipViewAlbumsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/artists/${artistId}/view/singles?" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        ).body().actualInstance as RelationshipViewAlbumsResponse
    }

    suspend fun getLibraryArtistAlbums(
        artistId: String,
        limit: Int,
        offset: Int,
    ): RelationshipViewAlbumsResponse {
        return ciderMusicApi.apiV1AmapiRunV3Post(
            ApiV1AmapiRunV3PostRequest(
                path = "/v1/me/library/artists/${artistId}/albums?" +
                        "include=catalog" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        ).body().actualInstance as RelationshipViewAlbumsResponse
    }

    enum class SearchType(val type: String) {
        Songs("songs"),
        Albums("albums"),
        Artists("artists"),
        Playlists("playlists"),
        PlaylistFolders("playlist-folders"),
    }

    enum class InLibrarySearchType(val type: String) {
        Songs("library-songs"),
        Playlists("library-playlists"),
        Albums("library-albums"),
        Artists("library-artists"),
    }

    companion object {
        const val DEFAULT_LIMIT = 20
        const val DEFAULT_OFFSET = 0
    }
}
