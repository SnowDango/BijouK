package com.snowdango.bijouk.features.album

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.snowdango.bijouk.infla.SharedEventStore
import com.snowdango.bijouk.model.cider.CiderBridgeModel
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.CiderRPCModel
import com.snowdango.bijouk.model.cider.data.AlbumDetailData
import com.snowdango.bijouk.model.cider.data.entity.SongData
import com.snowdango.bijouk.ui.data.ActionResultType
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class AlbumDetailViewModel(
    private val baseUrl: String,
    private val token: String,
    private val albumId: String,
    private val isLibrary: Boolean,
) : ViewModel(), KoinComponent {

    private val sharedEventStore: SharedEventStore by inject()
    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }
    private val ciderRPCModel: CiderRPCModel by inject { parametersOf(baseUrl, token) }
    private val ciderBridgeModel: CiderBridgeModel by inject { parametersOf(baseUrl, token) }
    private val applicationScope: CoroutineScope by inject()

    private val _albumDetailDataFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val albumDetailDataFlow = _albumDetailDataFlow.stateIn(
        applicationScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _albumDetailDataFlow.value,
    )
    val albumSongsFlow: Flow<PagingData<SongData>> = Pager(
        config = PagingConfig(
            pageSize = 100,
            initialLoadSize = 100,
        )
    ) {
        ciderModel.getAlbumSongsPagingSource(isLibrary, albumId)
    }.flow.cachedIn(viewModelScope)
    private val _actionResultFlow: MutableSharedFlow<ActionResultType> = MutableSharedFlow()
    val actionResultFlow = _actionResultFlow.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
    )

    init {
        load()
    }

    private fun load() = viewModelScope.launch(Dispatchers.IO) {
        getAlbumDetail()
    }

    private fun getAlbumDetail() = viewModelScope.launch(Dispatchers.IO) {
        _albumDetailDataFlow.emit(UiState.Loading)
        try {
            val albumDetail = if (isLibrary) {
                ciderModel.getLibraryAlbumDetails(albumId)
            } else {
                ciderModel.getAlbumDetails(albumId)
            }
            if (albumDetail != null) {
                _albumDetailDataFlow.emit(UiState.Success(albumDetail))
            } else {
                _albumDetailDataFlow.emit(UiState.Error)
            }
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("AlbumDetailViewModel", th.toString())
            _albumDetailDataFlow.emit(UiState.Error)
        }
    }

    fun songPlay(songId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playSongById(songId)
            _actionResultFlow.emit(ActionResultType.SongPlay)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("AlbumDetailViewModel", th.toString())
        }
    }

    fun songPlayNext(songId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playNextSongById(songId)
            _actionResultFlow.emit(ActionResultType.SongPlayNext)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("AlbumDetailViewModel", th.toString())
        }
    }

    fun songPlayLater(songId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playLaterSongById(songId)
            _actionResultFlow.emit(ActionResultType.SongPlayLater)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("AlbumDetailViewModel", th.toString())
        }
    }

    fun albumPlay(albumId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playAlbumById(albumId)
            _actionResultFlow.emit(ActionResultType.AlbumPlay)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("AlbumDetailViewModel", th.toString())
        }
    }

    fun albumPlayShuffled(albumId: String) = applicationScope.launch {
        try {
            ciderBridgeModel.playAlbumByIdShuffled(albumId)
            _actionResultFlow.emit(ActionResultType.AlbumPlayShuffled)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("AlbumDetailViewModel", th.toString())
        }
    }

    fun clearActionResult() = viewModelScope.launch(Dispatchers.IO) {
        _actionResultFlow.emit(ActionResultType.None)
    }

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val albumDetailData: AlbumDetailData) : UiState()
        data object Error : UiState()
    }
}
