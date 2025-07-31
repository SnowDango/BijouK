package com.snowdango.bijouk.features.search.playlist

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.snowdango.bijouk.features.search.R
import com.snowdango.bijouk.features.search.playlist.component.SearchPlaylistCard
import com.snowdango.bijouk.features.search.playlist.component.SearchPlaylistFolderCard
import com.snowdango.bijouk.model.cider.data.entity.PlaylistData
import com.snowdango.bijouk.model.cider.data.entity.PlaylistFoldersData
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SearchPlaylistScreen(
    baseUrl: String,
    token: String,
    sheetMinSize: Dp,
    searchPlaylistFolders: LazyPagingItems<PlaylistFoldersData>?,
    searchPlaylist: LazyPagingItems<PlaylistData>,
    modifier: Modifier = Modifier,
    viewModel: SearchPlaylistViewModel = koinViewModel<SearchPlaylistViewModel> {
        parametersOf(baseUrl, token)
    },
) {
    val scrollState = rememberScrollState()
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val cellCount = if (maxWidth < 600.dp) 2 else 3
        LazyVerticalGrid(
            columns = GridCells.Fixed(cellCount),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = sheetMinSize,
            ),
        ) {
            if (searchPlaylistFolders != null && searchPlaylistFolders.itemCount > 0) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = stringResource(R.string.search_library_category_folders),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                    )
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    PlaylistFolderContent(
                        searchPlaylistFolders = searchPlaylistFolders,
                        cardWidthSize = (this@BoxWithConstraints.maxWidth - 16.dp) / 3,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = stringResource(R.string.search_library_category_playlists),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                    )
                }
            }
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

@Composable
fun PlaylistFolderContent(
    searchPlaylistFolders: LazyPagingItems<PlaylistFoldersData>,
    cardWidthSize: Dp,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(
            horizontal = 8.dp
        )
    ) {
        items(count = searchPlaylistFolders.itemCount) { index ->
            val playlistFolder = searchPlaylistFolders[index]
            playlistFolder?.let {
                SearchPlaylistFolderCard(
                    playlistFoldersData = it,
                    onClickPlay = {},
                    onClickPlayNext = {},
                    onClickPlayLater = {},
                    modifier = Modifier
                        .width(cardWidthSize)
                )
            }
        }
    }
}
