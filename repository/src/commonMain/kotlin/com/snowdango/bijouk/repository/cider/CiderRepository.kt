package com.snowdango.bijouk.repository.cider


import com.snowdango.bijouk.domain.api.CiderApi
import com.snowdango.bijouk.domain.api.CiderSocket
import com.snowdango.bijouk.domain.api.event.NowPlayingItemDidChangeEvent
import com.snowdango.bijouk.domain.api.event.NowPlayingStatusDidChange
import com.snowdango.bijouk.domain.api.event.PlayBackStateDidChangeEvent
import com.snowdango.bijouk.domain.api.event.PlayBackTimeDidChangeEvent
import com.snowdango.bijouk.domain.api.response.ArtistFullAlbumResponse
import com.snowdango.bijouk.domain.api.response.ArtistSingleResponse
import com.snowdango.bijouk.domain.api.response.ArtistsResponse
import com.snowdango.bijouk.domain.api.response.ArtistsTopSongResponse
import com.snowdango.bijouk.domain.api.response.BasicResponse
import com.snowdango.bijouk.domain.api.response.NowPlayingResponse
import com.snowdango.bijouk.domain.api.response.SearchInLibraryResponse
import com.snowdango.bijouk.domain.api.response.SearchResponse
import com.snowdango.bijouk.domain.api.response.data.QueueResponseData

class CiderRepository(
    private val ciderApi: CiderApi,
    private val ciderSocket: CiderSocket,
) {

    suspend fun getActive(): BasicResponse {
        return ciderApi.active()
    }

    suspend fun getNowPlay(): NowPlayingResponse {
        return ciderApi.nowPlaying()
    }

    suspend fun getQueue(): List<QueueResponseData> {
        return ciderApi.getQueue()
    }

    suspend fun postPlayPause(): BasicResponse {
        return ciderApi.playPause()
    }

    suspend fun postNext(): BasicResponse {
        return ciderApi.next()
    }

    suspend fun postPrev(): BasicResponse {
        return ciderApi.previous()
    }

    suspend fun seekTo(to: Float): BasicResponse {
        return ciderApi.seekTo(to)
    }

    suspend fun postMoveQueue(index: Int, moveIndex: Int): BasicResponse {
        return ciderApi.moveQueue(index, moveIndex)
    }

    suspend fun changeQueueIndex(index: Int): BasicResponse {
        return ciderApi.changeQueueIndex(index)
    }

    suspend fun songPlayById(id: String): BasicResponse {
        return ciderApi.songPlayById(id)
    }

    suspend fun albumPlayById(id: String): BasicResponse {
        return ciderApi.albumPlayById(id)
    }

    suspend fun playlistPlayById(id: String): BasicResponse {
        return ciderApi.playlistPlayById(id)
    }

    suspend fun songPlayNextById(id: String): BasicResponse {
        return ciderApi.songPlayNextById(id)
    }

    suspend fun albumPlayNextById(id: String): BasicResponse {
        return ciderApi.albumPlayNextById(id)
    }

    suspend fun playlistPlayNextById(id: String): BasicResponse {
        return ciderApi.playlistPlayNextById(id)
    }

    suspend fun songPlayLaterById(id: String): BasicResponse {
        return ciderApi.songPlayLaterById(id)
    }

    suspend fun albumPlayLaterById(id: String): BasicResponse {
        return ciderApi.albumPlayLaterById(id)
    }

    suspend fun playlistPlayLaterById(id: String): BasicResponse {
        return ciderApi.playlistPlayLaterById(id)
    }

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
    ): SearchInLibraryResponse {
        return ciderApi.searchInLibrarySongs(query, offset, limit)
    }

    suspend fun searchInLibraryAlbums(
        query: String,
        offset: Int,
        limit: Int
    ): SearchInLibraryResponse {
        return ciderApi.searchInLibraryAlbums(query, offset, limit)
    }

    suspend fun searchInLibraryArtists(
        query: String,
        offset: Int,
        limit: Int
    ): SearchInLibraryResponse {
        return ciderApi.searchInLibraryArtists(query, offset, limit)
    }

    suspend fun searchInLibraryPlaylists(
        query: String,
        offset: Int,
        limit: Int
    ): SearchInLibraryResponse {
        return ciderApi.searchInLibraryPlaylists(query, offset, limit)
    }

    suspend fun getArtistDetails(artistId: String): ArtistsResponse {
        return ciderApi.getArtistDetails(artistId)
    }

    suspend fun getArtistTopSongs(
        artistId: String,
        limit: Int,
        offset: Int
    ): ArtistsTopSongResponse {
        return ciderApi.getArtistTopSongs(artistId, limit, offset)
    }

    suspend fun getArtistFullAlbums(
        artistId: String,
        limit: Int,
        offset: Int,
    ): ArtistFullAlbumResponse {
        return ciderApi.getArtistFullAlbums(artistId, limit, offset)
    }

    suspend fun getArtistSingles(
        artistId: String,
        limit: Int,
        offset: Int,
    ): ArtistSingleResponse {
        return ciderApi.getArtistSingles(artistId, limit, offset)
    }

    suspend fun stationPlayById(stationId: String): BasicResponse {
        return ciderApi.stationPlayById(stationId)
    }

    fun connect(
        onConnect: () -> Unit,
        onDisConnect: () -> Unit,
        onTimeChangeEvent: (PlayBackTimeDidChangeEvent) -> Unit,
        onStateChangeEvent: (PlayBackStateDidChangeEvent) -> Unit,
        onNowPlayingItemChangeEvent: (NowPlayingItemDidChangeEvent) -> Unit,
        onNowPlayingStatusChangeEvent: (NowPlayingStatusDidChange) -> Unit
    ) {
        ciderSocket.startSocket(
            onConnect,
            onDisConnect,
            onTimeChangeEvent,
            onStateChangeEvent,
            onNowPlayingItemChangeEvent,
            onNowPlayingStatusChangeEvent
        )
    }

    fun disconnect() {
        ciderSocket.closeSocket()
    }
}
