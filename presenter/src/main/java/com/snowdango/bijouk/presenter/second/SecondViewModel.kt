package com.snowdango.bijouk.presenter.second

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.infla.SharedEventStore
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.CiderRPCModel
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SecondViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {

    val sharedEventStore: SharedEventStore by inject()

    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }
    private val ciderRPCModel: CiderRPCModel by inject { parametersOf(baseUrl, token) }

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
    private val _isChangeableSeekFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isChangeableSeekFlow = _isChangeableSeekFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _isChangeableSeekFlow.value,
    )

    private val playBackEventListener = object : CiderModel.PlayBackStatusEventListener {
        override fun onTimeChangeEvent(playBackTimeData: PlayBackTimeData) {
            viewModelScope.launch(Dispatchers.IO) {
                _playbackTimeFlow.emit(playBackTimeData)
            }
        }

        override fun onStateChangeEvent(
            nowPlayData: NowPlayData?,
            playBackTimeData: PlayBackTimeData?
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                nowPlayData?.let { _nowPlayFlow.emit(it) }
                playBackTimeData?.let { _playbackTimeFlow.emit(it) }
            }
        }

        override fun onNowPlayingItemChangeEvent(nowPlayData: NowPlayData) {
            viewModelScope.launch(Dispatchers.IO) {
                _nowPlayFlow.emit(nowPlayData)
                sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueUpdated)
            }
        }

        override fun onNowPlayingStatusChangeEvent(nowPlayingStatusData: NowPlayingStatusData) {
            viewModelScope.launch(Dispatchers.IO) {
                _nowPlayingStatusFlow.emit(nowPlayingStatusData)
                sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueUpdated)
            }
        }
    }

    private val socketConnectionEventListener = object : CiderModel.SocketConnectionEventListener {
        override fun onConnect() {
            viewModelScope.launch(Dispatchers.IO) {
                _connectionStateFlow.emit(true)
            }
            nowPlayLoad()
        }

        override fun onDisConnect() {
            viewModelScope.launch(Dispatchers.IO) {
                _connectionStateFlow.emit(false)
            }
        }
    }

    init {
        nowPlayLoad()
        ciderModel.connect(socketConnectionEventListener, playBackEventListener)
    }

    private fun nowPlayLoad() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val data = ciderRPCModel.getNowPlay()
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

    fun playPause() = viewModelScope.launch(Dispatchers.IO) {
        try {
            ciderRPCModel.playPause()
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun next() = viewModelScope.launch(Dispatchers.IO) {
        try {
            ciderRPCModel.next()
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun prev() = viewModelScope.launch(Dispatchers.IO) {
        try {
            ciderRPCModel.prev()
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun seekTo(time: Float) = viewModelScope.launch(Dispatchers.IO) {
        _isChangeableSeekFlow.emit(false)
        try {
            ciderRPCModel.seekTo(time)
            _isChangeableSeekFlow.emit(true)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
            _isChangeableSeekFlow.emit(true)
        }
    }
}
