package com.snowdango.bijouk.features.search.songs

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import com.snowdango.bijouk.model.cider.data.entity.SongData
import com.snowdango.bijouk.ui.component.song.PlayableSongCard
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import com.snowdango.bijouk.ui.R as UiRes

@Composable
fun SearchSongsScreen(
    baseUrl: String,
    token: String,
    sheetMinSize: Dp,
    searchSongs: LazyPagingItems<SongData>,
    modifier: Modifier = Modifier,
    viewModel: SearchSongsViewModel = koinViewModel<SearchSongsViewModel>(
        parameters = { parametersOf(baseUrl, token) }
    ),
) {
    val context = LocalContext.current
    val actionResult = viewModel.actionResultFlow.collectAsStateWithLifecycle(
        initialValue = SearchSongsViewModel.SongAction.None,
    )

    LaunchedEffect(actionResult.value) {
        when (actionResult.value) {
            SearchSongsViewModel.SongAction.Play -> {
                Toast.makeText(context, UiRes.string.song_action_toast_play, Toast.LENGTH_SHORT)
                    .show()
                viewModel.clearActionResult()
            }

            SearchSongsViewModel.SongAction.PlayNext -> {
                Toast.makeText(
                    context,
                    UiRes.string.song_action_toast_play_next,
                    Toast.LENGTH_SHORT
                )
                    .show()
                viewModel.clearActionResult()
            }

            SearchSongsViewModel.SongAction.PlayLater -> {
                Toast.makeText(
                    context,
                    UiRes.string.song_action_toast_play_later,
                    Toast.LENGTH_SHORT
                )
                    .show()
                viewModel.clearActionResult()
            }

            SearchSongsViewModel.SongAction.None -> {
                // No action
            }
        }
    }

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
                    PlayableSongCard(
                        song = song,
                        onClickPlay = viewModel::searchSongPlay,
                        onClickPlayNext = viewModel::searchSongPlayNext,
                        onClickPlayLater = viewModel::searchSongPlayLater,
                    )
                }
            }
        }
    }
}
