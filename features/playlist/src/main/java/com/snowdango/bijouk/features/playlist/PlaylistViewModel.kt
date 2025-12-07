package com.snowdango.bijouk.features.playlist

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
import com.snowdango.bijouk.model.cider.data.entity.PlaylistData
import com.snowdango.bijouk.model.cider.data.entity.SongData
import com.snowdango.bijouk.ui.data.ActionResultType
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
import kotlin.coroutines.cancellation.CancellationException

class PlaylistViewModel(
    private val baseUrl: String,
    private val token: String,
    private val playlistId: String,
    private val isLibrary: Boolean
) : ViewModel(), KoinComponent {

    private val applicationScope: CoroutineScope by inject()
    private val sharedEventStore: SharedEventStore by inject()
    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }
    private val ciderRPCModel: CiderRPCModel by inject { parametersOf(baseUrl, token) }
    private val ciderBridgeModel: CiderBridgeModel by inject { parametersOf(baseUrl, token) }

    private val _playlistDetailState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val playlistDetailState = _playlistDetailState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _playlistDetailState.value
    )
    val playlistSongs: Flow<PagingData<SongData>> = Pager(
        config = PagingConfig(
            pageSize = 25,
            initialLoadSize = 25,
        )
    ) {
        ciderModel.getPlaylistSongsPagingSource(isLibrary, playlistId)
    }.flow.cachedIn(viewModelScope)
    private val _actionResultFlow: MutableSharedFlow<ActionResultType> = MutableSharedFlow()
    val actionResultFlow = _actionResultFlow.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
    )

    init {
        getPlaylistDetail()
    }

    private fun getPlaylistDetail() = viewModelScope.launch(Dispatchers.IO) {
        _playlistDetailState.emit(UiState.Loading)
        try {
            val playlistData = ciderModel.getPlaylistDetails(isLibrary, playlistId)
            if (playlistData != null) {
                _playlistDetailState.emit(UiState.Success(playlistData))
            } else {
                _playlistDetailState.emit(UiState.Error)
            }
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("PlaylistViewModel", th.toString())
            _playlistDetailState.emit(UiState.Error)
        }
    }

    fun songPlay(songId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playSongById(songId)
            _actionResultFlow.emit(ActionResultType.SongPlay)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("PlaylistViewModel", th.toString())
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
            Log.e("PlaylistViewModel", th.toString())
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
            Log.e("PlaylistViewModel", th.toString())
        }
    }

    fun playlistPlay(playlistId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playPlaylistById(playlistId)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("PlaylistViewModel", th.toString())
        }
    }

    fun playlistPlayShuffled(playlistId: String) = applicationScope.launch {
        try {
            ciderBridgeModel.playPlaylistByIdShuffled(playlistId)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("PlaylistViewModel", th.toString())
        }
    }

    fun clearActionResult() = viewModelScope.launch(Dispatchers.IO) {
        _actionResultFlow.emit(ActionResultType.None)
    }

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val playlistData: PlaylistData) : UiState()
        data object Error : UiState()
    }

}
