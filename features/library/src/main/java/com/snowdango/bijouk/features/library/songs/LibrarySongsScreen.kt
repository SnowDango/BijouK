package com.snowdango.bijouk.features.library.songs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.snowdango.bijouk.features.search.songs.component.SearchSongCard
import com.snowdango.bijouk.model.cider.data.SearchSong
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun LibrarySongsScreen(
    baseUrl: String,
    token: String,
    sheetMinSize: Dp,
    searchSongs: LazyPagingItems<SearchSong>,
    modifier: Modifier = Modifier,
    viewModel: LibrarySongsViewModel = koinViewModel<LibrarySongsViewModel>(
        parameters = { parametersOf(baseUrl, token) }
    ),
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = sheetMinSize,
            ),
            modifier = Modifier
                .fillMaxSize(),
        ) {
            items(count = searchSongs.itemCount) { index ->
                val song = searchSongs[index]
                song?.let {
                    SearchSongCard(
                        searchSong = song,
                        onClickPlay = viewModel::searchSongPlay,
                        onClickPlayNext = viewModel::searchSongPlayNext,
                        onClickPlayLater = viewModel::searchSongPlayLater,
                    )
                }
            }
        }
    }
}
