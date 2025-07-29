package com.snowdango.bijouk.model.cider

import com.snowdango.bijouk.model.cider.data.ArtistDetailData
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.model.cider.data.QueueDataList
import com.snowdango.bijouk.model.cider.data.SearchData
import com.snowdango.bijouk.model.cider.data.entity.Album
import com.snowdango.bijouk.model.cider.data.entity.Song
import com.snowdango.bijouk.model.cider.mapper.api.convert
import com.snowdango.bijouk.model.cider.mapper.event.convert
import com.snowdango.bijouk.model.cider.paging.library.LibraryAllAlbumsPagingSource
import com.snowdango.bijouk.model.cider.paging.library.LibraryAllArtistsPagingSource
import com.snowdango.bijouk.model.cider.paging.library.LibraryAllPlaylistsPagingSource
import com.snowdango.bijouk.model.cider.paging.library.LibraryAllSongsPagingSource
import com.snowdango.bijouk.model.cider.paging.library.SearchInLibraryAlbumsPagingSource
import com.snowdango.bijouk.model.cider.paging.library.SearchInLibraryArtistsPagingSource
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

    suspend fun isActive(): Boolean {
        return try {
            val response = repository.getActive()
            response.status == "ok"
        } catch (_: Throwable) {
            false
        }
    }

    suspend fun getNowPlay(): Triple<NowPlayData, PlayBackTimeData, NowPlayingStatusData> {
        val nowPlay = repository.getNowPlay()
        return nowPlay.convert()
    }

    suspend fun playPause() {
        repository.postPlayPause()
    }

    suspend fun next() {
        repository.postNext()
    }

    suspend fun prev() {
        repository.postPrev()
    }

    suspend fun seekTo(to: Float) {
        repository.seekTo(to)
    }

    suspend fun moveQueue(index: Int, moveIndex: Int) {
        repository.postMoveQueue(index, moveIndex)
    }

    suspend fun changeQueueIndex(index: Int) {
        repository.changeQueueIndex(index)
    }

    suspend fun songPlayById(id: String) {
        repository.songPlayById(id)
    }

    suspend fun albumPlayById(id: String) {
        repository.albumPlayById(id)
    }

    suspend fun playlistPlayById(id: String) {
        repository.playlistPlayById(id)
    }

    suspend fun songPlayNextById(id: String) {
        repository.songPlayNextById(id)
    }

    suspend fun albumPlayNextById(id: String) {
        repository.albumPlayNextById(id)
    }

    suspend fun playlistPlayNextById(id: String) {
        repository.playlistPlayNextById(id)
    }

    suspend fun songPlayLaterById(id: String) {
        repository.songPlayLaterById(id)
    }

    suspend fun albumPlayLaterById(id: String) {
        repository.albumPlayLaterById(id)
    }

    suspend fun playlistPlayLaterById(id: String) {
        repository.playlistPlayLaterById(id)
    }

    suspend fun getQueue(): QueueDataList {
        val queues = repository.getQueue()
        val currentIndex = queues.indexOfLast { it.attributes.currentPlaybackTime != null }
        return QueueDataList(
            list = queues.mapIndexed { index, data -> data.convert(index, currentIndex) },
        )
    }

    suspend fun searchAll(query: String): SearchData {
        val result = repository.searchAll(query)
        return result.convert()
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

    fun getLibraryAllSongsPagingSource(): LibraryAllSongsPagingSource {
        return LibraryAllSongsPagingSource(repository)
    }

    fun getLibraryAllAlbumsPagingSource(): LibraryAllAlbumsPagingSource {
        return LibraryAllAlbumsPagingSource(repository)
    }

    fun getLibraryAllArtistsPagingSource(): LibraryAllArtistsPagingSource {
        return LibraryAllArtistsPagingSource(repository)
    }

    fun getLibraryAllPlaylistsPagingSource(): LibraryAllPlaylistsPagingSource {
        return LibraryAllPlaylistsPagingSource(repository)
    }

    suspend fun getArtistDetails(artistId: String): ArtistDetailData? {
        val response = repository.getArtistDetails(artistId)
        return response.convert()
    }

    suspend fun getArtistTopSongs(artistId: String, limit: Int = 20, offset: Int = 0): List<Song>? {
        val response = repository.getArtistTopSongs(artistId, limit, offset)
        return response.convert()
    }

    suspend fun getArtistFullAlbums(
        artistId: String,
        limit: Int = 20,
        offset: Int = 0
    ): List<Album>? {
        val response = repository.getArtistFullAlbums(artistId, limit, offset)
        return response.convert()
    }

    suspend fun getArtistSingles(
        artistId: String,
        limit: Int = 20,
        offset: Int = 0
    ): List<Album>? {
        val response = repository.getArtistSingles(artistId, limit, offset)
        return response.convert()
    }

    suspend fun stationPlayById(stationId: String) {
        repository.stationPlayById(stationId)
    }

    fun connect(
        connectionEventListener: SocketConnectionEventListener,
        playBackEventListener: PlayBackStatusEventListener,
    ) {
        repository.connect(
            onConnect = {
                connectionEventListener.onConnect()
            },
            onDisConnect = {
                connectionEventListener.onDisConnect()
            },
            onTimeChangeEvent = {
                playBackEventListener.onTimeChangeEvent(it.convert())
            },
            onStateChangeEvent = {
                val data = it.convert()
                playBackEventListener.onStateChangeEvent(data.first, data.second)
            },
            onNowPlayingItemChangeEvent = {
                playBackEventListener.onNowPlayingItemChangeEvent(it.convert())
            },
            onNowPlayingStatusChangeEvent = {
                playBackEventListener.onNowPlayingStatusChangeEvent(it.convert())
            },
        )
    }

    fun disconnect() {
        repository.disconnect()
    }

    interface PlayBackStatusEventListener {
        fun onTimeChangeEvent(playBackTimeData: PlayBackTimeData)
        fun onStateChangeEvent(nowPlayData: NowPlayData?, playBackTimeData: PlayBackTimeData?)
        fun onNowPlayingItemChangeEvent(nowPlayData: NowPlayData)
        fun onNowPlayingStatusChangeEvent(nowPlayingStatusData: NowPlayingStatusData)
    }

    interface SocketConnectionEventListener {
        fun onConnect()
        fun onDisConnect()
    }
}
