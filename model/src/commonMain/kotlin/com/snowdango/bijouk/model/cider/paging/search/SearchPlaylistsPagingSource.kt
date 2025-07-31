package com.snowdango.bijouk.model.cider.paging.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.snowdango.bijouk.model.cider.data.entity.PlaylistData
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import com.snowdango.bijouk.repository.cider.CiderRepository
import kotlin.coroutines.cancellation.CancellationException

class SearchPlaylistsPagingSource(
    private val query: String,
    private val repository: CiderRepository,
) : PagingSource<Int, PlaylistData>() {

    override fun getRefreshKey(state: PagingState<Int, PlaylistData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaylistData> {
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
                val playlists =
                    response.data.results.playlists?.data?.map { it.convert() }.orEmpty()
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