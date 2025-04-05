package com.snowdango.bijouk.features.nowPlay.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.features.nowPlay.NowPlayViewModel
import com.snowdango.bijouk.features.nowPlay.component.QueueSongCard
import com.snowdango.bijouk.model.cider.data.QueueData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueueContent(
    queueViewData: NowPlayViewModel.QueueViewData?,
    sheetSize: Dp,
    onClickNext: (index: Int) -> Unit,
    onRefreshQueue: () -> Unit,
    modifier: Modifier = Modifier,
    onClickSkip: () -> Unit
) {
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = queueViewData?.isRefresh == true,
        onRefresh = { onRefreshQueue.invoke() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = sheetSize,
                )
            ) {
                queueViewData?.let { viewData ->
                    viewData.queueDataList.list.forEachIndexed { index, queueData ->
                        if (queueData.state == QueueData.State.Waiting) {
                            item {
                                QueueSongCard(
                                    queueData = queueData,
                                    onClickNext = {
                                        onClickNext.invoke(index)
                                    },
                                    onClickSkip = {
                                        onClickSkip.invoke()
                                    },
                                )
                            }
                        }
                    }
                }

                /*item {
                    Spacer(
                        modifier = Modifier
                            .height(sheetSize)
                            .fillMaxWidth()
                    )
                }*/
            }
        }
    }
}
