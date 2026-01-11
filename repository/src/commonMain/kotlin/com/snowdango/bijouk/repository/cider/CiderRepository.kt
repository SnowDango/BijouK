package com.snowdango.bijouk.repository.cider


import com.snowdango.bijouk.api.CiderMusicApi
import com.snowdango.bijouk.api.model.AlbumsResponse
import com.snowdango.bijouk.api.model.AmapiRunV3PostRequest
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
import io.ktor.util.reflect.typeInfo
import net.thauvin.erik.urlencoder.UrlEncoderUtil

class CiderRepository(
    private val ciderMusicApi: CiderMusicApi
) {

    suspend fun searchAll(query: String): SearchResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${SearchType.entries.joinToString(",") { it.type }}" +
                        "&limit=$DEFAULT_LIMIT" +
                        "&offset=$DEFAULT_OFFSET"
            )
        ).typedBody(typeInfo<SearchResponse>())
    }

    suspend fun searchSongs(query: String, offset: Int, limit: Int): SearchResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${SearchType.Songs.type}" +
                        "&limit=${limit}" +
                        "&offset=${offset}"
            )
        ).typedBody(typeInfo<SearchResponse>())
    }

    suspend fun searchAlbums(query: String, offset: Int, limit: Int): SearchResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${SearchType.Albums.type}" +
                        "&limit=${limit}" +
                        "&offset=${offset}"
            )
        ).typedBody(typeInfo<SearchResponse>())
    }

    suspend fun searchArtists(query: String, offset: Int, limit: Int): SearchResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${SearchType.Artists.type}" +
                        "&limit=${limit}" +
                        "&offset=${offset}"
            )
        ).typedBody(typeInfo<SearchResponse>())
    }

    suspend fun searchPlaylists(query: String, offset: Int, limit: Int): SearchResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/social/jp/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=playlists" +
                        "&limit=$limit" +
                        "&offset=$offset"
            )
        ).typedBody(typeInfo<SearchResponse>())
    }

    suspend fun searchInLibrarySongs(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${InLibrarySearchType.Songs.type}" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).typedBody(typeInfo<LibrarySearchResponse>())
    }

    suspend fun searchInLibraryAlbums(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${InLibrarySearchType.Albums.type}" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).typedBody(typeInfo<LibrarySearchResponse>())
    }

    suspend fun searchInLibraryArtists(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${InLibrarySearchType.Artists.type}" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).typedBody(typeInfo<LibrarySearchResponse>())
    }

    suspend fun searchInLibraryPlaylists(
        query: String,
        offset: Int,
        limit: Int
    ): LibrarySearchResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/search?" +
                        "term=${UrlEncoderUtil.encode(query)}" +
                        "&types=${InLibrarySearchType.Playlists.type}" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).typedBody(typeInfo<LibrarySearchResponse>())
    }

    suspend fun libraryAllSongs(
        limit: Int,
        offset: Int
    ): LibrarySongsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/${SearchType.Songs.type}?" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).typedBody(typeInfo<LibrarySongsResponse>())
    }

    suspend fun libraryAllAlbums(
        limit: Int,
        offset: Int
    ): LibraryAlbumsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/${SearchType.Albums.type}?" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).typedBody(typeInfo<LibraryAlbumsResponse>())
    }

    suspend fun libraryAllArtists(
        limit: Int,
        offset: Int
    ): LibraryArtistsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/${SearchType.Albums.type}?" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).typedBody(typeInfo<LibraryArtistsResponse>())
    }

    suspend fun libraryAllPlaylists(
        limit: Int,
        offset: Int
    ): LibraryPlaylistsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/${SearchType.Playlists.type}?" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).typedBody(typeInfo<LibraryPlaylistsResponse>())
    }

    suspend fun libraryAllPlaylistFolders(
        limit: Int,
        offset: Int
    ): LibraryPlaylistFoldersResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/${SearchType.PlaylistFolders.type}?" +
                        "&limit=$limit" +
                        "&offset=$offset" +
                        "&include=catalog"
            )
        ).typedBody(typeInfo<LibraryPlaylistFoldersResponse>())
    }

    suspend fun libraryPlaylistFolderChildren(
        folderId: String,
        limit: Int,
        offset: Int
    ): LibraryPlaylistsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/playlist-folders/${folderId}/children?" +
                        "limit=$limit" +
                        "&offset=$offset"
            )
        ).typedBody(typeInfo<LibraryPlaylistsResponse>())
    }

    suspend fun getAlbumDetails(albumId: String): AlbumsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/albums/$albumId"
            )
        ).typedBody(typeInfo<AlbumsResponse>())
    }

    suspend fun getLibraryAlbumDetails(albumId: String): LibraryAlbumsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/albums/$albumId"
            )
        ).typedBody(typeInfo<LibraryAlbumsResponse>())
    }

    suspend fun getArtistDetails(artistId: String): ArtistsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/artists/$artistId?" +
                        "include=default-playable-content"
            )
        ).typedBody(typeInfo<ArtistsResponse>())
    }

    suspend fun getLibraryArtistDetails(artistId: String): LibraryArtistsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/artists/$artistId?" +
                        "include=default-playable-content"
            )
        ).typedBody(typeInfo<LibraryArtistsResponse>())
    }

    suspend fun getPlaylistDetails(
        playlistId: String
    ): PlaylistsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/playlists/$playlistId"
            )
        ).typedBody(typeInfo<PlaylistsResponse>())
    }

    suspend fun getLibraryPlaylistDetails(
        playlistId: String
    ): LibraryPlaylistsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/playlists/$playlistId"
            )
        ).typedBody(typeInfo<LibraryPlaylistsResponse>())
    }

    suspend fun getPlaylistTracks(
        playlistId: String,
        limit: Int,
        offset: Int = 0,
    ): SongsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/playlists/$playlistId/tracks?" +
                        "&limit=$limit" +
                        "&offset=$offset"
            )
        ).typedBody(typeInfo<SongsResponse>())
    }

    suspend fun getLibraryPlaylistTracks(
        playlistId: String,
        limit: Int,
        offset: Int = 0,
    ): LibrarySongsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/playlists/$playlistId/tracks?" +
                        "&limit=$limit" +
                        "&offset=$offset"
            )
        ).typedBody(typeInfo<LibrarySongsResponse>())
    }

    suspend fun getAlbumTracks(
        albumId: String,
        limit: Int,
        offset: Int = 0,
    ): RelationshipViewSongsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/albums/${albumId}/tracks?" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        ).typedBody(typeInfo<RelationshipViewSongsResponse>())
    }

    suspend fun getLibraryAlbumTracks(
        albumId: String,
        limit: Int,
        offset: Int = 0,
    ): RelationshipViewLibrarySongsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/albums/${albumId}/tracks" +
                        "?include=catalog" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        ).typedBody(typeInfo<RelationshipViewLibrarySongsResponse>())
    }

    suspend fun getArtistTopSongs(
        artistId: String,
        limit: Int,
        offset: Int
    ): RelationshipViewSongsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/artists/${artistId}/view/top-songs?" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        ).typedBody(typeInfo<RelationshipViewSongsResponse>())
    }

    suspend fun getArtistFullAlbums(
        artistId: String,
        limit: Int,
        offset: Int,
    ): RelationshipViewAlbumsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/artists/${artistId}/view/full-albums?" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        ).typedBody(typeInfo<RelationshipViewAlbumsResponse>())
    }

    suspend fun getArtistSingles(
        artistId: String,
        limit: Int,
        offset: Int,
    ): RelationshipViewAlbumsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/catalog/jp/artists/${artistId}/view/singles?" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        ).typedBody(typeInfo<RelationshipViewAlbumsResponse>())
    }

    suspend fun getLibraryArtistAlbums(
        artistId: String,
        limit: Int,
        offset: Int,
    ): RelationshipViewAlbumsResponse {
        return ciderMusicApi.amapiRunV3Post(
            AmapiRunV3PostRequest(
                path = "/v1/me/library/artists/${artistId}/albums?" +
                        "include=catalog" +
                        "limit=${limit}" +
                        "&offset=${offset}"
            )
        ).typedBody(typeInfo<RelationshipViewAlbumsResponse>())
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
