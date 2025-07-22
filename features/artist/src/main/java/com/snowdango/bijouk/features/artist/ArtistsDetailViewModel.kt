package com.snowdango.bijouk.features.artist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.data.ArtistDetailData
import com.snowdango.bijouk.model.cider.data.entity.Album
import com.snowdango.bijouk.model.cider.data.entity.Song
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class ArtistsDetailViewModel(
    private val baseUrl: String,
    private val token: String,
    private val artistId: String,
) : ViewModel(), KoinComponent {

    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }

    private val _artistDetailDataFlow: MutableStateFlow<ArtistDetailData?> = MutableStateFlow(null)
    val artistDetailDataFlow = _artistDetailDataFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _artistDetailDataFlow.value
    )
    private val _artistTopSongsFlow: MutableStateFlow<List<Song>?> = MutableStateFlow(null)
    val artistTopSongsFlow = _artistTopSongsFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _artistTopSongsFlow.value
    )
    private val _artistFullAlbumsFlow: MutableStateFlow<List<Album>?> = MutableStateFlow(null)
    val artistFullAlbumsFlow = _artistFullAlbumsFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _artistFullAlbumsFlow.value
    )
    private val _artistSinglesFlow: MutableStateFlow<List<Album>?> = MutableStateFlow(null)
    val artistSinglesFlow = _artistSinglesFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _artistSinglesFlow.value
    )

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        getArtistDetail()
        getArtistTopSongs()
        getArtistFullAlbums()
        getArtistSingles()
    }

    private fun getArtistDetail() = viewModelScope.launch {
        try {
            val artistDetail = ciderModel.getArtistDetails(artistId)
            _artistDetailDataFlow.emit(artistDetail)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("ArtistsDetailViewModel", th.toString())
            _artistDetailDataFlow.emit(null)
        }
    }

    private fun getArtistTopSongs() = viewModelScope.launch {
        try {
            val topSongs = ciderModel.getArtistTopSongs(artistId)
            _artistTopSongsFlow.emit(topSongs)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("ArtistsDetailViewModel", th.toString())
            _artistTopSongsFlow.emit(null)
        }
    }

    private fun getArtistFullAlbums() = viewModelScope.launch {
        try {
            val albums = ciderModel.getArtistFullAlbums(artistId)
            _artistFullAlbumsFlow.emit(albums)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("ArtistsDetailViewModel", th.toString())
            _artistFullAlbumsFlow.emit(null)
        }
    }

    private fun getArtistSingles() = viewModelScope.launch {
        try {
            val singles = ciderModel.getArtistSingles(artistId)
            _artistSinglesFlow.emit(singles)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("ArtistsDetailViewModel", th.toString())
            _artistSinglesFlow.emit(null)
        }
    }
}
