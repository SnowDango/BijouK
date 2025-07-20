package com.snowdango.bijouk.features.nowPlay.view.search.songs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.snowdango.bijouk.features.nowPlay.action.SearchSongsAction
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.data.SearchSong
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SearchSongsViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {

    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }

    private var query: String = ""
    val searchSongsFlow: Flow<PagingData<SearchSong>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            initialLoadSize = 30,
        )
    ) {
        ciderModel.getSearchSongsPagingSource(query)
    }.flow.cachedIn(viewModelScope)

    private val _searchSongsPlayActionCompleteFlow: MutableStateFlow<SearchSongsPlayAction> =
        MutableStateFlow(SearchSongsPlayAction.NoAction)
    val searchSongsPlayActionCompleteFlow = _searchSongsPlayActionCompleteFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _searchSongsPlayActionCompleteFlow.value,
    )

    fun onSearchSongsAction(action: SearchSongsAction) {
        when (action) {
            is SearchSongsAction.Blank -> clearSearchSongs()
            is SearchSongsAction.SearchSongs -> searchSongs(action.query)
        }
    }

    private fun searchSongs(query: String) {
        this.query = query
    }

    private fun clearSearchSongs() {
        query = ""
    }

    fun searchSongPlay(songId: String) = viewModelScope.launch {
        try {
            ciderModel.songPlayById(songId)
            _searchSongsPlayActionCompleteFlow.emit(SearchSongsPlayAction.Play)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchSongPlayNext(songId: String) = viewModelScope.launch {
        try {
            ciderModel.songPlayNextById(songId)
            _searchSongsPlayActionCompleteFlow.emit(SearchSongsPlayAction.PlayNext)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchSongPlayLater(songId: String) = viewModelScope.launch {
        try {
            ciderModel.songPlayLaterById(songId)
            _searchSongsPlayActionCompleteFlow.emit(SearchSongsPlayAction.PlayLater)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun clearSearchSongsPlayActionComplete() {
        viewModelScope.launch {
            _searchSongsPlayActionCompleteFlow.emit(SearchSongsPlayAction.NoAction)
        }
    }

    enum class SearchSongsPlayAction {
        Play,
        PlayNext,
        PlayLater,
        NoAction,
    }
}
