package com.snowdango.bijouk.features.queue

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.infla.SharedEventStore
import com.snowdango.bijouk.model.cider.CiderRPCModel
import com.snowdango.bijouk.model.cider.data.QueueData
import com.snowdango.bijouk.model.cider.data.QueueDataList
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import java.time.Duration

class QueueViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {

    private val sharedEventStore: SharedEventStore by inject()
    private val ciderRPCModel: CiderRPCModel by inject { parametersOf(baseUrl, token) }
    private val applicationScope: CoroutineScope by inject()

    private val _queueViewDataFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val queueViewDataFlow = _queueViewDataFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _queueViewDataFlow.value,
    )
    private val _isRefreshFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRefreshFlow = _isRefreshFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _isRefreshFlow.value,
    )

    init {
        queueLoad()
        initEventListener()
    }

    fun initEventListener() = viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedEventStore.events.collect { event ->
                when (event) {
                    is SharedEventStore.SharedEvent.QueueUpdated -> queueRefresh()
                    is SharedEventStore.SharedEvent.QueueDelayUpdated -> queueDelayRefresh()
                    else -> {
                        // 他のイベントは無視
                    }
                }
            }
        }
    }

    // queueに変更を加えた際に遅延して更新しないと反映されない
    fun queueDelayRefresh() = viewModelScope.launch(Dispatchers.IO) {
        delay(Duration.ofSeconds(2))
        queueLoad()
    }

    fun queueRefresh() = viewModelScope.launch(Dispatchers.IO) {
        _isRefreshFlow.emit(true)
        queueLoad()
    }

    private fun queueLoad() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val data = ciderRPCModel.getQueue()
            _isRefreshFlow.emit(false)
            _queueViewDataFlow.emit(UiState.Success(data))
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
            _isRefreshFlow.emit(false)
            _queueViewDataFlow.emit(UiState.Error)
        }
    }

    fun moveQueueNext(index: Int) = applicationScope.launch {
        _queueViewDataFlow.value.let { uiState ->
            try {
                if (uiState !is UiState.Success) return@launch
                val nextIndex = uiState.queueDataList.list
                    .indexOfFirst { it.state == QueueData.State.Current } + 1
                ciderRPCModel.moveQueue(index, nextIndex)
                val queueDataList = uiState.queueDataList.copy(
                    list = uiState.queueDataList.list.toMutableList().also {
                        val data = it[index]
                        it.removeAt(index)
                        it.add(nextIndex, data)
                    }
                )
                _queueViewDataFlow.emit(UiState.Success(queueDataList))
            } catch (ce: CancellationException) {
                throw ce
            } catch (th: Throwable) {
                Log.e("NowPlayViewModel", th.toString())
                _queueViewDataFlow.emit(UiState.Error)
            }
        }
    }

    fun skipQueue(index: Int) = applicationScope.launch {
        try {
            ciderRPCModel.changeQueueIndex(index)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val queueDataList: QueueDataList) : UiState()
        data object Error : UiState()
    }
}
