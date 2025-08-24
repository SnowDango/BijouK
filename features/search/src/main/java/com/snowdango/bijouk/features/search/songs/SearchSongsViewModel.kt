package com.snowdango.bijouk.features.search.songs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.infla.SharedEventStore
import com.snowdango.bijouk.model.cider.CiderRPCModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SearchSongsViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {

    private val sharedEventStore: SharedEventStore by inject()
    private val ciderRPCModel: CiderRPCModel by inject { parametersOf(baseUrl, token) }
    private val applicationScope: CoroutineScope by inject()

    private val _actionResultFlow: MutableSharedFlow<SongAction> = MutableSharedFlow()
    val actionResultFlow = _actionResultFlow.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
    )

    fun searchSongPlay(songId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playSongById(songId)
            _actionResultFlow.emit(SongAction.Play)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchSongPlayNext(songId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playNextSongById(songId)
            _actionResultFlow.emit(SongAction.PlayNext)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchSongPlayLater(songId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playLaterSongById(songId)
            _actionResultFlow.emit(SongAction.PlayLater)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun clearActionResult() = viewModelScope.launch(Dispatchers.IO) {
        _actionResultFlow.emit(SongAction.None)
    }

    enum class SongAction {
        Play,
        PlayNext,
        PlayLater,
        None,
    }
}
