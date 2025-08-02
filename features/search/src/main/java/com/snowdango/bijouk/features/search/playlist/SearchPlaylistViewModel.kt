package com.snowdango.bijouk.features.search.playlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.infla.SharedEventStore
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.CiderRPCModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
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
    private val applicationScope: CoroutineScope by inject()

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

    fun searchPlaylistPlay(playlistId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playPlaylistById(playlistId)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("SearchPlaylistViewModel", th.toString())
        }
    }

    fun searchPlaylistPlayNext(playlistId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playNextPlaylistById(playlistId)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("SearchPlaylistViewModel", th.toString())
        }
    }

    fun searchPlaylistPlayLater(playlistId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playLaterPlaylistById(playlistId)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("SearchPlaylistViewModel", th.toString())
        }
    }

    private fun shuffleModeLoad() = applicationScope.launch {
        try {
            isShuffleMode = ciderRPCModel.getShuffleMode()
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("SearchPlaylistViewModel", th.toString())
        }
    }

    fun playPlaylistFolder(playlistFolderId: String) = applicationScope.launch {
        try {
            val children = ciderModel.getAllPlaylistFolderChildren(playlistFolderId)
            ciderRPCModel.playPlaylistFolderById(children.map { it.id })
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
