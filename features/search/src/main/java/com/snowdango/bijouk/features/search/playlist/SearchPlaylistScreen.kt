package com.snowdango.bijouk.features.search.playlist

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
import com.snowdango.bijouk.features.search.playlist.component.SearchPlaylistCard
import com.snowdango.bijouk.model.cider.data.entity.PlaylistData
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SearchPlaylistScreen(
    baseUrl: String,
    token: String,
    sheetMinSize: Dp,
    searchPlaylist: LazyPagingItems<PlaylistData>,
    modifier: Modifier = Modifier,
    viewModel: SearchPlaylistViewModel = koinViewModel<SearchPlaylistViewModel> {
        parametersOf(baseUrl, token)
    },
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
            items(count = searchPlaylist.itemCount) { index ->
                val playlist = searchPlaylist[index]
                playlist?.let {
                    SearchPlaylistCard(
                        searchPlaylist = it,
                        onClickPlay = viewModel::searchPlaylistPlay,
                        onClickPlayNext = viewModel::searchPlaylistPlayNext,
                        onClickPlayLater = viewModel::searchPlaylistPlayLater,
                    )
                }
            }
        }
    }
}
