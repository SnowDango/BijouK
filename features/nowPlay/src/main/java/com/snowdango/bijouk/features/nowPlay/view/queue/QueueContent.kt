package com.snowdango.bijouk.features.nowPlay.view.queue

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowdango.bijouk.features.nowPlay.action.QueueRefreshAction
import com.snowdango.bijouk.features.nowPlay.component.QueueSongCard
import com.snowdango.bijouk.model.cider.data.QueueData
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueueContent(
    baseUrl: String,
    token: String,
    queueRefreshAction: QueueRefreshAction,
    sheetSize: Dp,
    onClearQueueRefreshAction: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QueueViewModel = koinViewModel<QueueViewModel> {
        parametersOf(baseUrl, token)
    },
) {
    val queueViewData = viewModel.queueViewDataFlow.collectAsStateWithLifecycle()

    LaunchedEffect(queueRefreshAction) {
        viewModel.onQueueRefreshAction(queueRefreshAction)
        onClearQueueRefreshAction.invoke()
    }

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = queueViewData.value?.isRefresh == true,
        onRefresh = viewModel::queueRefresh,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = sheetSize,
                )
            ) {
                queueViewData.value?.let { viewData ->
                    viewData.queueDataList.list.forEachIndexed { index, queueData ->
                        if (queueData.state == QueueData.State.Waiting) {
                            item {
                                QueueSongCard(
                                    queueData = queueData,
                                    onClickNext = {
                                        viewModel.moveQueueNext(index)
                                    },
                                    onClickSkip = {
                                        viewModel.skipQueue(index)
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
