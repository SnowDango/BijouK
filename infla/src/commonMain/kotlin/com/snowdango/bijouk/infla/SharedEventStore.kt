package com.snowdango.bijouk.infla

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SharedEventStore(
    private val coroutineScope: CoroutineScope,
) {

    private val _events: MutableStateFlow<SharedEvent?> = MutableStateFlow(null)
    val events: Flow<SharedEvent?> = _events.stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(5_000),
        _events.value,
    )

    fun setEvent(event: SharedEvent) = coroutineScope.launch {
        _events.emit(event)
    }

    sealed class SharedEvent {
        data object QueueUpdated : SharedEvent()
        data object QueueDelayUpdated : SharedEvent()
        data class ShuffleModeUpdated(val isShuffle: Boolean) : SharedEvent()
        data object DeviceListUpdated : SharedEvent()
    }

}