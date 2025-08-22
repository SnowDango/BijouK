package com.snowdango.bijouk.model.cider

import com.snowdango.bijouk.model.cider.data.ArtistDetailData
import com.snowdango.bijouk.model.cider.data.entity.AlbumData
import com.snowdango.bijouk.model.cider.data.entity.PlaylistData
import com.snowdango.bijouk.model.cider.data.entity.SongData
import com.snowdango.bijouk.model.cider.mapper.api.convert
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import com.snowdango.bijouk.model.cider.paging.PlaylistSongsPagingSource
import com.snowdango.bijouk.model.cider.paging.library.SearchInLibraryAlbumsPagingSource
import com.snowdango.bijouk.model.cider.paging.library.SearchInLibraryArtistsPagingSource
import com.snowdango.bijouk.model.cider.paging.library.SearchInLibraryPlaylistFoldersPagingSource
import com.snowdango.bijouk.model.cider.paging.library.SearchInLibraryPlaylistsPagingSource
import com.snowdango.bijouk.model.cider.paging.library.SearchInLibrarySongsPagingSource
import com.snowdango.bijouk.model.cider.paging.search.SearchAlbumsPagingSource
import com.snowdango.bijouk.model.cider.paging.search.SearchArtistsPagingSource
import com.snowdango.bijouk.model.cider.paging.search.SearchPlaylistsPagingSource
import com.snowdango.bijouk.model.cider.paging.search.SearchSongsPagingSource
import com.snowdango.bijouk.repository.cider.CiderRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class CiderModel(
    baseUrl: String,
    token: String,
) : KoinComponent {

    private val repository: CiderRepository by inject<CiderRepository> {
        parametersOf(
            baseUrl,
            token
        )
    }

    fun getSearchSongsPagingSource(
        query: String,
    ): SearchSongsPagingSource {
        return SearchSongsPagingSource(query, repository)
    }

    fun getSearchAlbumsPagingSource(
        query: String,
    ): SearchAlbumsPagingSource {
        return SearchAlbumsPagingSource(query, repository)
    }

    fun getSearchArtistsPagingSource(
        query: String,
    ): SearchArtistsPagingSource {
        return SearchArtistsPagingSource(query, repository)
    }

    fun getSearchPlaylistsPagingSource(
        query: String,
    ): SearchPlaylistsPagingSource {
        return SearchPlaylistsPagingSource(query, repository)
    }

    fun getSearchInLibrarySongsPagingSource(
        query: String,
    ): SearchInLibrarySongsPagingSource {
        return SearchInLibrarySongsPagingSource(query, repository)
    }

    fun getSearchInLibraryAlbumsPagingSource(
        query: String,
    ): SearchInLibraryAlbumsPagingSource {
        return SearchInLibraryAlbumsPagingSource(query, repository)
    }

    fun getSearchInLibraryArtistsPagingSource(
        query: String,
    ): SearchInLibraryArtistsPagingSource {
        return SearchInLibraryArtistsPagingSource(query, repository)
    }

    fun getSearchInLibraryPlaylistsPagingSource(
        query: String,
    ): SearchInLibraryPlaylistsPagingSource {
        return SearchInLibraryPlaylistsPagingSource(query, repository)
    }

    fun getLibraryPlaylistFoldersPagingSource(
        query: String,
    ): SearchInLibraryPlaylistFoldersPagingSource {
        return SearchInLibraryPlaylistFoldersPagingSource(query, repository)
    }

    suspend fun getAllPlaylistFolderChildren(
        folderId: String,
    ): List<PlaylistData> {
        val playlists: MutableList<PlaylistData> = mutableListOf()
        var hasNext = true
        while (hasNext) {
            val response = repository.libraryPlaylistFolderChildren(
                folderId = folderId,
                limit = 100,
                offset = playlists.size
            )
            val children = response.data.data?.map { it.convert() }
            children?.let { playlists.addAll(it) }
            hasNext = response.data.next != null
        }
        return playlists
    }

    suspend fun getArtistDetails(artistId: String): ArtistDetailData? {
        val response = repository.getArtistDetails(artistId)
        return response.convert()
    }

    suspend fun getLibraryArtistDetails(artistId: String): ArtistDetailData? {
        val response = repository.getLibraryArtistDetails(artistId)
        return response.convert()
    }

    suspend fun getPlaylistDetails(
        isLibrary: Boolean,
        playlistId: String
    ): PlaylistData? {
        return if (isLibrary) {
            val response = repository.getLibraryPlaylistDetails(playlistId)
            response.data.data.firstOrNull()?.convert()
        } else {
            val response = repository.getPlaylistDetails(playlistId)
            response.data.data.firstOrNull()?.convert()
        }
    }

    fun getPlaylistSongsPagingSource(
        isLibrary: Boolean,
        playlistId: String,
    ): PlaylistSongsPagingSource {
        return PlaylistSongsPagingSource(
            isLibrary = isLibrary,
            playlistId = playlistId,
            repository = repository
        )
    }

    suspend fun getArtistTopSongs(
        artistId: String,
        limit: Int = 20,
        offset: Int = 0
    ): List<SongData>? {
        val response = repository.getArtistTopSongs(artistId, limit, offset)
        return response.convert()
    }

    suspend fun getArtistFullAlbums(
        artistId: String,
        limit: Int = 20,
        offset: Int = 0
    ): List<AlbumData>? {
        val response = repository.getArtistFullAlbums(artistId, limit, offset)
        return response.convert()
    }

    suspend fun getArtistSingles(
        artistId: String,
        limit: Int = 20,
        offset: Int = 0
    ): List<AlbumData>? {
        val response = repository.getArtistSingles(artistId, limit, offset)
        return response.convert()
    }

    suspend fun getLibraryArtistAlbums(
        artistId: String,
        limit: Int = 20,
        offset: Int = 0
    ): List<AlbumData>? {
        val response = repository.getLibraryArtistAlbums(artistId, limit, offset)
        return response.convert()
    }
}
