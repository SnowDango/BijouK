package com.snowdango.bijouk.model.cider.paging.library

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.snowdango.bijouk.model.cider.data.entity.PlaylistFoldersData
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import com.snowdango.bijouk.repository.cider.CiderRepository
import kotlin.coroutines.cancellation.CancellationException

class SearchInLibraryPlaylistFoldersPagingSource(
    private val query: String,
    private val repository: CiderRepository,
) : PagingSource<Int, PlaylistFoldersData>() {

    override fun getRefreshKey(state: PagingState<Int, PlaylistFoldersData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaylistFoldersData> {
        try {
            return if (query.isNotBlank()) {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            } else {
                val position = params.key ?: 0
                val response = repository.libraryAllPlaylistFolders(
                    limit = params.loadSize,
                    offset = position * params.loadSize
                )
                val folders = response.data.data?.map { it.convert() }.orEmpty()
                LoadResult.Page(
                    data = folders,
                    prevKey = if (position == 0) null else position - 1,
                    nextKey = if (folders.isEmpty()) null else position + 1,
                )
            }
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}