package com.snowdango.bijouk.features.search.songs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowdango.bijouk.model.cider.CiderModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SearchSongsViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {

    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }

    fun searchSongPlay(songId: String) = viewModelScope.launch {
        try {
            ciderModel.songPlayById(songId)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchSongPlayNext(songId: String) = viewModelScope.launch {
        try {
            ciderModel.songPlayNextById(songId)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchSongPlayLater(songId: String) = viewModelScope.launch {
        try {
            ciderModel.songPlayLaterById(songId)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }
}
