package com.snowdango.bijouk.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.data.SearchAlbum
import com.snowdango.bijouk.model.cider.data.SearchArtist
import com.snowdango.bijouk.model.cider.data.SearchSong
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SearchViewModel(
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
    val searchArtistsFlow: Flow<PagingData<SearchArtist>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            initialLoadSize = 30,
        )
    ) {
        ciderModel.getSearchArtistsPagingSource(query)
    }.flow.cachedIn(viewModelScope)
    val searchAlbumsFlow: Flow<PagingData<SearchAlbum>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            initialLoadSize = 30,
        )
    ) {
        ciderModel.getSearchAlbumsPagingSource(query)
    }.flow.cachedIn(viewModelScope)

    fun setQuery(query: String) {
        this.query = query
    }
}