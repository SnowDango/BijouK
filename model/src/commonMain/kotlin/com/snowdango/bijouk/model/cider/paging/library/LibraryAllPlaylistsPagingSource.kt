package com.snowdango.bijouk.model.cider.paging.library

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.snowdango.bijouk.model.cider.data.SearchPlaylist
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import com.snowdango.bijouk.repository.cider.CiderRepository
import kotlin.coroutines.cancellation.CancellationException

class LibraryAllPlaylistsPagingSource(
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
            val position = params.key ?: 0
            val response = repository.libraryAllPlaylists(
                limit = params.loadSize,
                offset = position * params.loadSize
            )
            val playlists = response.data.data?.map { it.convert() }.orEmpty()
            return LoadResult.Page(
                data = playlists,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (playlists.isEmpty()) null else position + 1,
            )
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}