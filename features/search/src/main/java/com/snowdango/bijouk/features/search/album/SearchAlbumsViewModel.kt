package com.snowdango.bijouk.features.search.album

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.model.cider.CiderModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SearchAlbumsViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {

    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }

    fun searchAlbumPlay(albumId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            ciderModel.albumPlayById(albumId)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchAlbumPlayNext(albumId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            ciderModel.albumPlayNextById(albumId)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchAlbumPlayLater(albumId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            ciderModel.albumPlayLaterById(albumId)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }
}
