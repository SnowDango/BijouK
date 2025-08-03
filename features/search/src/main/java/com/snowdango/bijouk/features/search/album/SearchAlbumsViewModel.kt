package com.snowdango.bijouk.features.search.album

import android.util.Log
import androidx.lifecycle.ViewModel
import com.snowdango.bijouk.infla.SharedEventStore
import com.snowdango.bijouk.model.cider.CiderRPCModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SearchAlbumsViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {

    private val sharedEventStore: SharedEventStore by inject()
    private val ciderRPCModel: CiderRPCModel by inject { parametersOf(baseUrl, token) }
    private val applicationScope: CoroutineScope by inject()

    fun searchAlbumPlay(albumId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playAlbumById(albumId)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchAlbumPlayNext(albumId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playNextAlbumById(albumId)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchAlbumPlayLater(albumId: String) = applicationScope.launch {
        try {
            ciderRPCModel.playLaterAlbumById(albumId)
            sharedEventStore.setEvent(SharedEventStore.SharedEvent.QueueDelayUpdated)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }
}
