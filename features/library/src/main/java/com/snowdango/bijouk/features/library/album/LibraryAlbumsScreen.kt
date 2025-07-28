package com.snowdango.bijouk.features.library.album

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.snowdango.bijouk.features.library.album.component.LibraryAlbumCard
import com.snowdango.bijouk.model.cider.data.SearchAlbum
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun LibraryAlbumsScreen(
    baseUrl: String,
    token: String,
    sheetMinSize: Dp,
    searchAlbums: LazyPagingItems<SearchAlbum>,
    modifier: Modifier = Modifier,
    viewModel: LibraryAlbumsViewModel = koinViewModel<LibraryAlbumsViewModel>(
        parameters = { parametersOf(baseUrl, token) }
    ),
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(
                if (maxWidth < 600.dp) 2 else 3
            ),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = sheetMinSize,
            )
        ) {
            items(count = searchAlbums.itemCount) { index ->
                val album = searchAlbums[index]
                album?.let {
                    LibraryAlbumCard(
                        searchAlbum = it,
                        onClickPlay = viewModel::searchAlbumPlay,
                        onClickPlayNext = viewModel::searchAlbumPlayNext,
                        onClickPlayLater = viewModel::searchAlbumPlayLater,
                    )
                }
            }
        }
    }
}
