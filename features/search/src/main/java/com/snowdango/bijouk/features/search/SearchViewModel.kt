package com.snowdango.bijouk.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.data.entity.AlbumData
import com.snowdango.bijouk.model.cider.data.entity.ArtistData
import com.snowdango.bijouk.model.cider.data.entity.PlaylistData
import com.snowdango.bijouk.model.cider.data.entity.SongData
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
    val searchSongsFlow: Flow<PagingData<SongData>> = Pager(
        config = PagingConfig(
            pageSize = 25,
            initialLoadSize = 25,
        )
    ) {
        ciderModel.getSearchSongsPagingSource(query)
    }.flow.cachedIn(viewModelScope)
    val searchArtistsFlow: Flow<PagingData<ArtistData>> = Pager(
        config = PagingConfig(
            pageSize = 25,
            initialLoadSize = 25,
        )
    ) {
        ciderModel.getSearchArtistsPagingSource(query)
    }.flow.cachedIn(viewModelScope)
    val searchAlbumsFlow: Flow<PagingData<AlbumData>> = Pager(
        config = PagingConfig(
            pageSize = 25,
            initialLoadSize = 25,
        )
    ) {
        ciderModel.getSearchAlbumsPagingSource(query)
    }.flow.cachedIn(viewModelScope)

    val searchPlaylistsFlow: Flow<PagingData<PlaylistData>> = Pager(
        config = PagingConfig(
            pageSize = 25,
            initialLoadSize = 25,
        )
    ) {
        ciderModel.getSearchPlaylistsPagingSource(query)
    }.flow.cachedIn(viewModelScope)

    fun setQuery(query: String) {
        this.query = query
    }
}
