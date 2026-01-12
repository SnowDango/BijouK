package com.snowdango.bijouk.model.cider.paging.library

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.snowdango.bijouk.model.cider.data.entity.ArtistData
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import com.snowdango.bijouk.repository.cider.CiderRepository
import kotlin.coroutines.cancellation.CancellationException

class SearchInLibraryArtistsPagingSource(
    private val query: String,
    private val repository: CiderRepository,
) : PagingSource<Int, ArtistData>() {

    override fun getRefreshKey(state: PagingState<Int, ArtistData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtistData> {
        try {
            val position = params.key ?: 0
            val artists = if (query.isBlank()) {
                val response = repository.libraryAllArtists(
                    limit = params.loadSize,
                    offset = position * params.loadSize
                )
                response.data.data?.map { it.convert() }.orEmpty()
            } else {
                val response = repository.searchInLibraryArtists(
                    query = query,
                    limit = params.loadSize,
                    offset = position * params.loadSize
                )
                response.data.results.libraryArtists?.data?.map { it.convert() }.orEmpty()
            }
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