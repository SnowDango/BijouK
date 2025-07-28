package com.snowdango.bijouk.features.search.playlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.infla.SharedEventStore
import com.snowdango.bijouk.model.cider.CiderModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SearchPlaylistViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {

    private val sharedEventStore: SharedEventStore by inject()
    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }


    fun searchPlaylistPlay(playlistId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            ciderModel.playlistPlayById(playlistId)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchPlaylistPlayNext(playlistId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            ciderModel.playlistPlayNextById(playlistId)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchPlaylistPlayLater(playlistId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            ciderModel.playlistPlayLaterById(playlistId)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

}