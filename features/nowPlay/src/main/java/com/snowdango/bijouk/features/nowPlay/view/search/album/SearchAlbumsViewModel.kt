package com.snowdango.bijouk.features.nowPlay.view.search.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.snowdango.bijouk.features.nowPlay.action.SearchAlbumsAction
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.data.SearchAlbum
import kotlinx.coroutines.flow.Flow
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


}