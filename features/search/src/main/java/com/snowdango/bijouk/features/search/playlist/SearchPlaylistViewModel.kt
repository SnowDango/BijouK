package com.snowdango.bijouk.features.search.playlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.infla.SharedEventStore
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.CiderRPCModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SearchPlaylistViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {

    private val sharedEventStore: SharedEventStore by inject()
    private val ciderRPCModel: CiderRPCModel by inject { parametersOf(baseUrl, token) }
    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }

    private var isShuffleMode: Boolean = false

    init {
        setEventListener()
        shuffleModeLoad()
    }

    fun setEventListener() = viewModelScope.launch(Dispatchers.IO) {
        sharedEventStore.events.collect { event ->
            when (event) {
                is SharedEventStore.SharedEvent.ShuffleModeUpdated -> {
                    isShuffleMode = event.isShuffle
                }

                else -> {
                    // Handle other events if necessary
                }
            }
        }
    }

    fun searchPlaylistPlay(playlistId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            ciderRPCModel.playlistPlayById(playlistId)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("SearchPlaylistViewModel", th.toString())
        }
    }

    fun searchPlaylistPlayNext(playlistId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            ciderRPCModel.playlistPlayNextById(playlistId)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("SearchPlaylistViewModel", th.toString())
        }
    }

    fun searchPlaylistPlayLater(playlistId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            ciderRPCModel.playlistPlayLaterById(playlistId)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("SearchPlaylistViewModel", th.toString())
        }
    }

    private fun shuffleModeLoad() = viewModelScope.launch(Dispatchers.IO) {
        try {
            isShuffleMode = ciderRPCModel.getShuffleMode()
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("SearchPlaylistViewModel", th.toString())
        }
    }

    fun playPlaylistFolder(playlistFolderId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val children = ciderModel.getAllPlaylistFolderChildren(playlistFolderId)
            ciderRPCModel.playlistFolderPlayById(children.map { it.id })
            delay(1_000)
            ciderRPCModel.setShuffleMode(true, isShuffleMode)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("SearchPlaylistViewModel", th.toString())
        }
    }
}
