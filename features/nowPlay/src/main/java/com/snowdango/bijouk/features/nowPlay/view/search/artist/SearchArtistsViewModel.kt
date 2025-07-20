package com.snowdango.bijouk.features.nowPlay.view.search.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.snowdango.bijouk.features.nowPlay.action.SearchArtistsAction
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.data.SearchArtist
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SearchArtistsViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {

    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }

    private var query: String = ""
    val searchArtistsFlow: Flow<PagingData<SearchArtist>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            initialLoadSize = 30,
        )
    ) {
        ciderModel.getSearchArtistsPagingSource(query)
    }.flow.cachedIn(viewModelScope)

    fun onSearchArtistsAction(action: SearchArtistsAction) {
        when (action) {
            is SearchArtistsAction.Blank -> clearSearchArtists()
            is SearchArtistsAction.SearchArtists -> searchArtists(action.query)
        }
    }

    private fun searchArtists(query: String) {
        this.query = query
    }

    private fun clearSearchArtists() {
        this.query = ""
    }

}