package com.snowdango.bijouk.features.search.playlist

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
    isLibrary: Boolean,
    searchPlaylistFolders: LazyPagingItems<PlaylistFoldersData>?,
    searchPlaylist: LazyPagingItems<PlaylistData>,
    onClickPlaylist: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchPlaylistViewModel = koinViewModel<SearchPlaylistViewModel> {
        parametersOf(baseUrl, token)
    },
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                bottom = sheetMinSize
            )
        ) {
            if (searchPlaylistFolders != null && searchPlaylistFolders.itemCount > 0) {
                item {
                    Text(
                        text = stringResource(R.string.search_library_category_folders),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                    )
                }
                item {
                    PlaylistFolderContent(
                        searchPlaylistFolders = searchPlaylistFolders,
                        cardWidthSize = (this@BoxWithConstraints.maxWidth - 16.dp) / 3,
                        onClickPlay = viewModel::playPlaylistFolder,
                        onClickShufflePlay = viewModel::playPlaylistFolderShuffled,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                item {
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
                        isLibrary,
                        onClickPlaylist = onClickPlaylist,
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
    onClickPlay: (String) -> Unit,
    onClickShufflePlay: (String) -> Unit,
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
                    onClickPlay = onClickPlay,
                    onClickShufflePlay = onClickShufflePlay,
                    modifier = Modifier
                        .width(cardWidthSize)
                )
            }
        }
    }
}
