package com.snowdango.bijouk.features.nowPlay.view.search.album

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.snowdango.bijouk.features.nowPlay.action.SearchAlbumsAction
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.data.SearchAlbum
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SearchAlbumsViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {

    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }

    private var query: String = ""
    val searchAlbumsFlow: Flow<PagingData<SearchAlbum>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            initialLoadSize = 30,
        )
    ) {
        ciderModel.getSearchAlbumsPagingSource(query)
    }.flow.cachedIn(viewModelScope)

    private val _searchAlbumsPlayActionCompleteFlow: MutableStateFlow<SearchAlbumsPlayAction> =
        MutableStateFlow(SearchAlbumsPlayAction.NoAction)
    val searchAlbumsPlayActionCompleteFlow = _searchAlbumsPlayActionCompleteFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _searchAlbumsPlayActionCompleteFlow.value,
    )

    fun onSearchAlbumsAction(action: SearchAlbumsAction) {
        when (action) {
            is SearchAlbumsAction.Blank -> clearSearchAlbums()
            is SearchAlbumsAction.SearchAlbums -> searchAlbums(action.query)
        }
    }

    private fun searchAlbums(query: String) {
        this.query = query
    }

    private fun clearSearchAlbums() {
        this.query = ""
    }

    fun searchAlbumPlay(albumId: String) = viewModelScope.launch {
        try {
            ciderModel.albumPlayById(albumId)
            _searchAlbumsPlayActionCompleteFlow.emit(SearchAlbumsPlayAction.Play)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchAlbumPlayNext(albumId: String) = viewModelScope.launch {
        try {
            ciderModel.albumPlayNextById(albumId)
            _searchAlbumsPlayActionCompleteFlow.emit(SearchAlbumsPlayAction.PlayNext)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun searchAlbumPlayLater(albumId: String) = viewModelScope.launch {
        try {
            ciderModel.albumPlayLaterById(albumId)
            _searchAlbumsPlayActionCompleteFlow.emit(SearchAlbumsPlayAction.PlayLater)
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("NowPlayViewModel", th.toString())
        }
    }

    fun clearSearchAlbumsPlayActionComplete() {
        viewModelScope.launch {
            _searchAlbumsPlayActionCompleteFlow.emit(SearchAlbumsPlayAction.NoAction)
        }
    }

    enum class SearchAlbumsPlayAction {
        Play,
        PlayNext,
        PlayLater,
        NoAction,
    }

}