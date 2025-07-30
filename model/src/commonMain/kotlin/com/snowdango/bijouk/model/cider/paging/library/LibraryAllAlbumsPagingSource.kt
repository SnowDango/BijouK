package com.snowdango.bijouk.model.cider.paging.library

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.snowdango.bijouk.model.cider.data.SearchAlbum
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import com.snowdango.bijouk.repository.cider.CiderRepository
import kotlin.coroutines.cancellation.CancellationException

class LibraryAllAlbumsPagingSource(
    private val repository: CiderRepository,
) : PagingSource<Int, SearchAlbum>() {

    override fun getRefreshKey(state: PagingState<Int, SearchAlbum>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchAlbum> {
        try {
            val position = params.key ?: 0
            val response = repository.libraryAllAlbums(
                limit = params.loadSize,
                offset = position * params.loadSize
            )
            val albums = response.data.data?.map { it.convert() }.orEmpty()
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