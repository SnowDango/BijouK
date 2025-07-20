package com.snowdango.bijouk.features.nowPlay.view.nowplay

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.model.cider.CiderModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class NowPlayingViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {

    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }
    private val _isChangeableSeekFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isChangeableSeekFlow = _isChangeableSeekFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _isChangeableSeekFlow.value,
    )

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

    fun seekTo(time: Float) = viewModelScope.launch {
        _isChangeableSeekFlow.emit(false)
        try {
            ciderModel.seekTo(time)
            _isChangeableSeekFlow.emit(true)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
            _isChangeableSeekFlow.emit(true)
        }
    }
}
