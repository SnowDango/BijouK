package com.snowdango.bijouk.features.nowPlay.view.queue

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.data.QueueData
import com.snowdango.bijouk.model.cider.data.QueueDataList
import kotlinx.coroutines.CancellationException
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

    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }

    private val _queueViewDataFlow: MutableStateFlow<QueueViewData?> = MutableStateFlow(null)
    val queueViewDataFlow = _queueViewDataFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _queueViewDataFlow.value,
    )

    init {
        queueLoad()
    }

    fun onQueueRefreshAction(action: QueueRefreshAction) = viewModelScope.launch {
        when (action) {
            QueueRefreshAction.NoAction -> {
                // Do nothing
            }

            QueueRefreshAction.Refresh -> {
                queueRefresh()
            }

            QueueRefreshAction.DelayRefresh -> {
                queueDelayRefresh()
            }
        }
    }

    // queueに変更を加えた際に遅延して更新しないと反映されない
    fun queueDelayRefresh() = viewModelScope.launch {
        delay(Duration.ofSeconds(2))
        queueLoad()
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

    fun moveQueueNext(index: Int) = viewModelScope.launch {
        _queueViewDataFlow.value?.let { queueViewData ->
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

    fun skipQueue(index: Int) = viewModelScope.launch {
        try {
            ciderModel.changeQueueIndex(index)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    data class QueueViewData(
        val queueDataList: QueueDataList,
        val isRefresh: Boolean,
    )

    enum class QueueRefreshAction {
        NoAction,
        Refresh,
        DelayRefresh,
    }

}