package com.snowdango.bijouk.features.queue

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowdango.bijouk.features.queue.component.QueueSongCard
import com.snowdango.bijouk.model.cider.data.QueueData
import com.snowdango.bijouk.ui.BijouKTheme
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueueScreen(
    name: String,
    baseUrl: String,
    token: String,
    sheetMinSize: Dp,
    onNavigateSearch: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QueueViewModel = koinViewModel<QueueViewModel> {
        parametersOf(baseUrl, token)
    },
) {
    val queueViewData = viewModel.queueViewDataFlow.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = name,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            onNavigateSearch.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
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
                        bottom = sheetMinSize,
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
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun QueueScreenPreview() {
    BijouKTheme {
        QueueScreen(
            name = "Queue",
            baseUrl = "https://example.com",
            token = "example_token",
            sheetMinSize = 100.dp,
            onNavigateSearch = {},
            modifier = Modifier.fillMaxSize(),
            viewModel = QueueViewModel("", "")
        )
    }
}

