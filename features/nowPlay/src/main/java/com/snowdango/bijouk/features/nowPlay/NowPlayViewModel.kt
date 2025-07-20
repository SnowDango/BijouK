package com.snowdango.bijouk.features.nowPlay

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.features.nowPlay.action.QueueRefreshAction
import com.snowdango.bijouk.features.nowPlay.action.SearchSongsAction
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf


class NowPlayViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {

    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }

    private val _connectionStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val connectionStateFlow = _connectionStateFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _connectionStateFlow.value,
    )
    private val _nowPlayFlow: MutableStateFlow<NowPlayData?> = MutableStateFlow(null)
    val nowPlayFlow = _nowPlayFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _nowPlayFlow.value,
    )
    private val _playbackTimeFlow: MutableStateFlow<PlayBackTimeData?> = MutableStateFlow(null)
    val playBackTimeData = _playbackTimeFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _playbackTimeFlow.value,
    )
    private val _nowPlayingStatusFlow: MutableStateFlow<NowPlayingStatusData> =
        MutableStateFlow(NowPlayingStatusData(false, false))
    val nowPlayingStatusFlow = _nowPlayingStatusFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _nowPlayingStatusFlow.value,
    )
    private val _queueRefreshActionFlow: MutableSharedFlow<QueueRefreshAction> =
        MutableSharedFlow()
    val queueRefreshActionFlow = _queueRefreshActionFlow.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
    )
    private val _searchSongsActionFlow: MutableStateFlow<SearchSongsAction> =
        MutableStateFlow(SearchSongsAction.Blank)
    val searchSongsActionFlow = _searchSongsActionFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _searchSongsActionFlow.value,
    )

    private val playBackEventListener = object : CiderModel.PlayBackStatusEventListener {
        override fun onTimeChangeEvent(playBackTimeData: PlayBackTimeData) {
            viewModelScope.launch {
                _playbackTimeFlow.emit(playBackTimeData)
            }
        }

        override fun onStateChangeEvent(
            nowPlayData: NowPlayData?,
            playBackTimeData: PlayBackTimeData?
        ) {
            viewModelScope.launch {
                nowPlayData?.let { _nowPlayFlow.emit(it) }
                playBackTimeData?.let { _playbackTimeFlow.emit(it) }
            }
        }

        override fun onNowPlayingItemChangeEvent(nowPlayData: NowPlayData) {
            viewModelScope.launch {
                _nowPlayFlow.emit(nowPlayData)
                _queueRefreshActionFlow.emit(QueueRefreshAction.Refresh)
            }
        }

        override fun onNowPlayingStatusChangeEvent(nowPlayingStatusData: NowPlayingStatusData) {
            viewModelScope.launch {
                _nowPlayingStatusFlow.emit(nowPlayingStatusData)
            }
        }
    }

    private val socketConnectionEventListener = object : CiderModel.SocketConnectionEventListener {
        override fun onConnect() {
            viewModelScope.launch {
                _connectionStateFlow.emit(true)
            }
            nowPlayLoad()
        }

        override fun onDisConnect() {
            viewModelScope.launch {
                _connectionStateFlow.emit(false)
            }
        }
    }

    init {
        nowPlayLoad()
        ciderModel.connect(socketConnectionEventListener, playBackEventListener)
    }

    private fun nowPlayLoad() = viewModelScope.launch {
        try {
            val data = ciderModel.getNowPlay()
            _nowPlayFlow.emit(data.first)
            _playbackTimeFlow.emit(data.second)
            _nowPlayingStatusFlow.emit(data.third)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
            _nowPlayFlow.emit(null)
            _playbackTimeFlow.emit(null)
        }
    }

    fun search(query: String) = viewModelScope.launch {
        _searchSongsActionFlow.emit(SearchSongsAction.SearchSongs(query))
    }

    fun searchClear() = viewModelScope.launch {
        _searchSongsActionFlow.emit(SearchSongsAction.Blank)
    }

    fun onQueueRefreshAction(queueRefreshAction: QueueRefreshAction) = viewModelScope.launch {
        _queueRefreshActionFlow.emit(queueRefreshAction)
    }

    fun clearQueueRefreshAction() = viewModelScope.launch {
        _queueRefreshActionFlow.emit(QueueRefreshAction.NoAction)
    }

    override fun onCleared() {
        super.onCleared()
        ciderModel.disconnect()
    }
}
