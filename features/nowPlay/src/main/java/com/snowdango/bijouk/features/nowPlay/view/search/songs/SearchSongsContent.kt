package com.snowdango.bijouk.features.nowPlay.view.search.songs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.snowdango.bijouk.features.nowPlay.action.QueueRefreshAction
import com.snowdango.bijouk.features.nowPlay.action.SearchSongsAction
import com.snowdango.bijouk.features.nowPlay.component.SearchSongCard
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SearchSongsContent(
    baseUrl: String,
    token: String,
    sheetSize: Dp,
    searchSongsAction: SearchSongsAction,
    onQueueRefreshAction: (QueueRefreshAction) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchSongsViewModel = koinViewModel<SearchSongsViewModel>(
        parameters = { parametersOf(baseUrl, token) }
    ),
) {
    val pagingSongs = viewModel.searchSongsFlow.collectAsLazyPagingItems()
    val playActionComplete =
        viewModel.searchSongsPlayActionCompleteFlow.collectAsStateWithLifecycle()

    LaunchedEffect(searchSongsAction) {
        viewModel.onSearchSongsAction(searchSongsAction)
        pagingSongs.refresh()
    }

    LaunchedEffect(playActionComplete.value) {
        when (playActionComplete.value) {
            SearchSongsViewModel.SearchSongsPlayAction.NoAction -> {}
            SearchSongsViewModel.SearchSongsPlayAction.Play -> {}
            SearchSongsViewModel.SearchSongsPlayAction.PlayNext -> onQueueRefreshAction(
                QueueRefreshAction.DelayRefresh
            )

            SearchSongsViewModel.SearchSongsPlayAction.PlayLater -> onQueueRefreshAction(
                QueueRefreshAction.DelayRefresh
            )
        }
        viewModel.clearSearchSongsPlayActionComplete()
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = sheetSize,
            ),
            modifier = Modifier
                .fillMaxSize(),
        ) {
            items(count = pagingSongs.itemCount) { index ->
                val song = pagingSongs[index]
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
