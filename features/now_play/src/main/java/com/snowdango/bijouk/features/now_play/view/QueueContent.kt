package com.snowdango.bijouk.features.now_play.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.snowdango.bijouk.features.now_play.NowPlayViewModel
import com.snowdango.bijouk.features.now_play.component.QueueSongCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueueContent(
    queueViewData: NowPlayViewModel.QueueViewData?,
    sheetSize: Dp,
    modifier: Modifier = Modifier,
    onRefreshQueue: () -> Unit,
) {
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = queueViewData?.isRefresh == true,
        onRefresh = { onRefreshQueue.invoke() }
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
        ) {
            queueViewData?.let { viewData ->
                items(viewData.queueDataList.list.filter { it.playbackType == 0 }) {
                    QueueSongCard(
                        title = it.name,
                        artist = it.artist,
                        artwork = it.artwork,
                    )
                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .height(sheetSize)
                        .fillMaxWidth()
                )
            }
        }
    }
}
