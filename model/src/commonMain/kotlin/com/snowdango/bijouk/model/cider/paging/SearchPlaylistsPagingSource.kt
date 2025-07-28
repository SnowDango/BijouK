package com.snowdango.bijouk.model.cider.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.snowdango.bijouk.model.cider.data.SearchPlaylist
import com.snowdango.bijouk.model.cider.mapper.converter.convertSearch
import com.snowdango.bijouk.repository.cider.CiderRepository
import kotlin.coroutines.cancellation.CancellationException

class SearchPlaylistsPagingSource(
    private val query: String,
    private val repository: CiderRepository,
) : PagingSource<Int, SearchPlaylist>() {

    override fun getRefreshKey(state: PagingState<Int, SearchPlaylist>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchPlaylist> {
        try {
            return if (query.isBlank()) {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            } else {
                val position = params.key ?: 0
                val response = repository.searchPlaylists(
                    query = query,
                    limit = params.loadSize,
                    offset = position * params.loadSize
                )
                val playlists = response.data.results.playlists?.convertSearch() ?: emptyList()
                LoadResult.Page(
                    data = playlists,
                    prevKey = if (position == 0) null else position - 1,
                    nextKey = if (playlists.isEmpty()) null else position + 1,
                )
            }
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}