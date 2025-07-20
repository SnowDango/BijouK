package com.snowdango.bijouk.features.nowPlay.view.search.album

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.snowdango.bijouk.features.nowPlay.action.SearchAlbumsAction
import com.snowdango.bijouk.features.nowPlay.component.SearchAlbumCard
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun SearchAlbumsContent(
    baseUrl: String,
    token: String,
    sheetSize: Dp,
    searchAlbumsAction: SearchAlbumsAction,
    modifier: Modifier = Modifier,
    viewModel: SearchAlbumsViewModel = koinViewModel<SearchAlbumsViewModel>(
        parameters = { parametersOf(baseUrl, token) }
    ),
) {

    val pagingAlbums = viewModel.searchAlbumsFlow.collectAsLazyPagingItems()

    LaunchedEffect(searchAlbumsAction) {
        viewModel.onSearchAlbumsAction(searchAlbumsAction)
        pagingAlbums.refresh()
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = sheetSize,
            )
        ) {
            items(count = pagingAlbums.itemCount) { index ->
                val album = pagingAlbums[index]
                album?.let {
                    SearchAlbumCard(
                        it,
                    )
                }
            }
        }
    }
}