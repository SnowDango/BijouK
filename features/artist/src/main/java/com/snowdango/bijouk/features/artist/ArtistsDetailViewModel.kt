package com.snowdango.bijouk.features.artist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.CiderRPCModel
import com.snowdango.bijouk.model.cider.data.ArtistDetailData
import com.snowdango.bijouk.model.cider.data.entity.AlbumData
import com.snowdango.bijouk.model.cider.data.entity.SongData
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    private val ciderRPCModel: CiderRPCModel by inject { parametersOf(baseUrl, token) }
    private val applicationScope: CoroutineScope by inject()

    private val _artistDetailDataFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val artistDetailDataFlow = _artistDetailDataFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _artistDetailDataFlow.value
    )
    private val _artistTopSongsFlow: MutableStateFlow<List<SongData>?> = MutableStateFlow(null)
    val artistTopSongsFlow = _artistTopSongsFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _artistTopSongsFlow.value
    )
    private val _artistFullAlbumsFlow: MutableStateFlow<List<AlbumData>?> = MutableStateFlow(null)
    val artistFullAlbumsFlow = _artistFullAlbumsFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _artistFullAlbumsFlow.value
    )
    private val _artistSinglesFlow: MutableStateFlow<List<AlbumData>?> = MutableStateFlow(null)
    val artistSinglesFlow = _artistSinglesFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _artistSinglesFlow.value
    )

    init {
        load()
    }

    private fun load() = viewModelScope.launch(Dispatchers.IO) {
        getArtistDetail()
        getArtistTopSongs()
        getArtistFullAlbums()
        getArtistSingles()
    }

    private fun getArtistDetail() = viewModelScope.launch(Dispatchers.IO) {
        _artistDetailDataFlow.emit(UiState.Loading)
        try {
            val artistDetail = ciderModel.getArtistDetails(artistId)
            if (artistDetail != null) {
                _artistDetailDataFlow.emit(UiState.Success(artistDetail))
            } else {
                _artistDetailDataFlow.emit(UiState.Error)
            }
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("ArtistsDetailViewModel", th.toString())
            _artistDetailDataFlow.emit(UiState.Error)
        }
    }

    private fun getArtistTopSongs() = viewModelScope.launch(Dispatchers.IO) {
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

    private fun getArtistFullAlbums() = viewModelScope.launch(Dispatchers.IO) {
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

    private fun getArtistSingles() = viewModelScope.launch(Dispatchers.IO) {
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

    fun stationPlayById(stationId: String) = applicationScope.launch {
        try {
            ciderRPCModel.stationPlayById(stationId)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("ArtistsDetailViewModel", th.toString())
        }
    }

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val artistDetailData: ArtistDetailData) : UiState()
        data object Error : UiState()
    }
}
