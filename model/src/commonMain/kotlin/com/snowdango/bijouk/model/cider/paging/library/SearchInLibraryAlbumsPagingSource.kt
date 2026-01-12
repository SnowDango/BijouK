package com.snowdango.bijouk.model.cider.paging.library

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.snowdango.bijouk.model.cider.data.entity.AlbumData
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import com.snowdango.bijouk.repository.cider.CiderRepository
import kotlin.coroutines.cancellation.CancellationException

class SearchInLibraryAlbumsPagingSource(
    private val query: String,
    private val repository: CiderRepository,
) : PagingSource<Int, AlbumData>() {

    override fun getRefreshKey(state: PagingState<Int, AlbumData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AlbumData> {
        try {
            val position = params.key ?: 0
            val albums = if (query.isBlank()) {
                val response = repository.libraryAllAlbums(
                    limit = params.loadSize,
                    offset = position * params.loadSize
                )
                response.data.data?.map { it.convert() }.orEmpty()
            } else {
                val response = repository.searchInLibraryAlbums(
                    query = query,
                    limit = params.loadSize,
                    offset = position * params.loadSize
                )
                response.data.results.libraryAlbums?.data?.map { it.convert() }.orEmpty()
            }
            return LoadResult.Page(
                data = albums,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (albums.isEmpty()) null else position + 1,
            )
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}