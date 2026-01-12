package com.snowdango.bijouk.features.queue

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowdango.bijouk.features.queue.component.QueueSongCard
import com.snowdango.bijouk.model.cider.data.QueueData
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.component.ProgressContent
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueueScreen(
    isNeedTitle: Boolean,
    name: String,
    baseUrl: String,
    token: String,
    sheetMinSize: Dp,
    onNavigateSearch: () -> Unit,
    onNavigateLibrary: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QueueViewModel = koinViewModel<QueueViewModel> {
        parametersOf(baseUrl, token)
    },
) {
    val context = LocalContext.current
    val queueViewData = viewModel.queueViewDataFlow.collectAsStateWithLifecycle()
    val isRefreshing = viewModel.isRefreshFlow.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.queueRefresh()
    }

    LaunchedEffect(queueViewData.value) {
        if (queueViewData.value is QueueViewModel.UiState.Error) {
            Toast.makeText(context, R.string.queue_loading_error_toast, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    if (isNeedTitle) {
                        Text(
                            text = name,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onNavigateLibrary.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.LibraryMusic,
                            contentDescription = null
                        )
                    }
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
            isRefreshing = isRefreshing.value,
            onRefresh = viewModel::queueRefresh,
        ) {
            when (queueViewData.value) {
                is QueueViewModel.UiState.Loading -> {
                    ProgressContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = sheetMinSize)
                    )
                }

                is QueueViewModel.UiState.Success -> {
                    val queueDataList =
                        (queueViewData.value as QueueViewModel.UiState.Success).queueDataList
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = 16.dp,
                            )
                        ) {
                            queueDataList.list.forEachIndexed { index, queueData ->
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
                            item {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(sheetMinSize)
                                )
                            }
                        }
                    }
                }

                is QueueViewModel.UiState.Error -> {
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
            isNeedTitle = true,
            name = "Queue",
            baseUrl = "https://example.com",
            token = "example_token",
            sheetMinSize = 100.dp,
            onNavigateSearch = {},
            onNavigateLibrary = {},
            modifier = Modifier.fillMaxSize(),
            viewModel = QueueViewModel("", "")
        )
    }
}
