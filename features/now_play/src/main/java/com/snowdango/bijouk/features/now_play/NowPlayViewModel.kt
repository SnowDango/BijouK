package com.snowdango.bijouk.features.now_play

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.model.cider.data.QueueData
import com.snowdango.bijouk.model.cider.data.QueueDataList
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class NowPlayViewModel(
    private val id: Long,
    private val name: String,
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
    private val _queueViewDataFlow: MutableStateFlow<QueueViewData?> = MutableStateFlow(null)
    val queueViewDataFlow = _queueViewDataFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _queueViewDataFlow.value,
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
                queueRefresh()
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
        queueLoad()
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

    fun queueRefresh() = viewModelScope.launch {
        val currentData = _queueViewDataFlow.value
        _queueViewDataFlow.emit(
            currentData?.copy(isRefresh = true)
        )
        queueLoad()
    }

    private fun queueLoad() = viewModelScope.launch {
        try {
            val data = ciderModel.getQueue()
            _queueViewDataFlow.emit(
                QueueViewData(queueDataList = data, isRefresh = false)
            )
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
            _queueViewDataFlow.emit(
                QueueViewData(queueDataList = QueueDataList(listOf()), isRefresh = false)
            )
        }
    }

    fun playPause() = viewModelScope.launch {
        try {
            ciderModel.playPause()
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun next() = viewModelScope.launch {
        try {
            ciderModel.next()
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun prev() = viewModelScope.launch {
        try {
            ciderModel.prev()
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun moveQueueNext(index: Int) = viewModelScope.launch {
        _queueViewDataFlow.value?.let { queueViewData->
            try {
                val nextIndex = queueViewData.queueDataList.list
                    .indexOfFirst { it.state == QueueData.State.Current } + 1
                ciderModel.moveQueue(index, nextIndex)
                val currentViewData = queueViewData.copy(
                    queueDataList = queueViewData.queueDataList.copy(
                        list = queueViewData.queueDataList.list.toMutableList().also {
                            val data = it[index]
                            it.removeAt(index)
                            it.add(nextIndex, data)
                        }
                    )
                )
                _queueViewDataFlow.emit(currentViewData)
            } catch (ce: CancellationException) {
                throw ce
            } catch (th: Throwable) {
                Log.e("NowPlayViewModel", th.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        ciderModel.disconnect()
    }

    data class QueueViewData(
        val queueDataList: QueueDataList,
        val isRefresh: Boolean,
    )
}
