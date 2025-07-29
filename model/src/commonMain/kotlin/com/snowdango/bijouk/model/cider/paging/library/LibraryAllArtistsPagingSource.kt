package com.snowdango.bijouk.model.cider.paging.library

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.snowdango.bijouk.model.cider.data.SearchArtist
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import com.snowdango.bijouk.repository.cider.CiderRepository
import kotlin.coroutines.cancellation.CancellationException

class LibraryAllArtistsPagingSource(
    private val repository: CiderRepository,
) : PagingSource<Int, SearchArtist>() {

    override fun getRefreshKey(state: PagingState<Int, SearchArtist>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchArtist> {
        try {
            val position = params.key ?: 0
            val response = repository.libraryAllArtists(
                limit = params.loadSize,
                offset = position * params.loadSize
            )
            val artists = response.data.data?.map { it.convert() }.orEmpty()
            return LoadResult.Page(
                data = artists,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (artists.isEmpty()) null else position + 1,
            )
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}