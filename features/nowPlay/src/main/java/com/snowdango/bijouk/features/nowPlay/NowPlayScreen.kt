package com.snowdango.bijouk.features.nowPlay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowdango.bijouk.features.nowPlay.action.QueueRefreshAction
import com.snowdango.bijouk.features.nowPlay.action.SearchAlbumsAction
import com.snowdango.bijouk.features.nowPlay.action.SearchArtistsAction
import com.snowdango.bijouk.features.nowPlay.action.SearchSongsAction
import com.snowdango.bijouk.features.nowPlay.component.NowPlayTopBar
import com.snowdango.bijouk.features.nowPlay.view.nowplay.BottomNowPlayingContent
import com.snowdango.bijouk.features.nowPlay.view.queue.QueueContent
import com.snowdango.bijouk.features.nowPlay.view.search.album.SearchAlbumsContent
import com.snowdango.bijouk.features.nowPlay.view.search.artist.SearchArtistsContent
import com.snowdango.bijouk.features.nowPlay.view.search.songs.SearchSongsContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowPlayScreen(
    name: String,
    baseUrl: String,
    token: String,
    modifier: Modifier = Modifier,
    viewModel: NowPlayViewModel = koinViewModel<NowPlayViewModel> {
        parametersOf(
            baseUrl,
            token
        )
    },
    onClickArtist: (artistId: String) -> Unit,
) {
    var sheetMaxHeight by remember { mutableStateOf(1000.dp) }
    val sheetMinHeight = 140.dp
    val destiny = LocalDensity.current

    @Suppress("MagicNumber")
    val imageMaxSize = with(destiny) { LocalView.current.width.toDp() * 3 / 5 }
    val screenHeight = LocalView.current.height
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded,
            skipHiddenState = true,
        )
    )
    var sheetHeight by remember { mutableStateOf(sheetMaxHeight) }

    val imageMinSize = 52.dp
    var imageSize by remember { mutableStateOf(0.dp) }

    val connectionState = viewModel.connectionStateFlow.collectAsStateWithLifecycle()
    val nowPlayData = viewModel.nowPlayFlow.collectAsStateWithLifecycle()
    val playBackTimeData = viewModel.playBackTimeData.collectAsStateWithLifecycle()
    val nowPlayingStatusData = viewModel.nowPlayingStatusFlow.collectAsStateWithLifecycle()
    val queueRefreshAction = viewModel.queueRefreshActionFlow.collectAsStateWithLifecycle(
        initialValue = QueueRefreshAction.NoAction
    )
    val searchSongsAction = viewModel.searchSongsActionFlow.collectAsStateWithLifecycle()
    val searchPlaylistsAction = viewModel.searchAlbumsActionFlow.collectAsStateWithLifecycle()
    val searchArtistsAction = viewModel.searchArtistsActionFlow.collectAsStateWithLifecycle()

    LaunchedEffect(sheetState.bottomSheetState.targetValue) {
        withContext(Dispatchers.Default) {
            var offset = sheetState.bottomSheetState.requireOffset()
            while (sheetState.bottomSheetState.currentValue != sheetState.bottomSheetState.targetValue) {
                if (offset != sheetState.bottomSheetState.requireOffset()) {
                    offset = sheetState.bottomSheetState.requireOffset()
                    val height = with(destiny) { (screenHeight - offset).toDp() }
                    sheetHeight = if (height < sheetMaxHeight) height else sheetMaxHeight
                }
            }
            if (sheetState.bottomSheetState.targetValue == sheetState.bottomSheetState.currentValue) {
                sheetHeight =
                    if (sheetState.bottomSheetState.targetValue == SheetValue.PartiallyExpanded) {
                        sheetMinHeight
                    } else {
                        sheetMaxHeight
                    }
            }
        }
    }

    LaunchedEffect(sheetHeight) {
        val par =
            (sheetHeight.value - sheetMinHeight.value) / ((sheetMaxHeight.value - sheetMinHeight.value) / 100)
        val parSize = (imageMaxSize.value - imageMinSize.value) / 100
        imageSize = (par * parSize).dp + imageMinSize
    }

    Box(modifier = modifier.fillMaxSize()) {
        BottomSheetScaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = sheetState,
            sheetPeekHeight = sheetMinHeight,
            sheetContent = {
                BottomNowPlayingContent(
                    baseUrl = baseUrl,
                    token = token,
                    sheetState = sheetState.bottomSheetState,
                    nowPlayData = nowPlayData.value,
                    playBackTimeData = playBackTimeData.value,
                    nowPlayingStatusData = nowPlayingStatusData.value,
                    sheetMaxHeight = sheetMaxHeight,
                    sheetHeight = sheetHeight,
                    imageSize = imageSize,
                )
            },
            topBar = {
                NowPlayTopBar(
                    name = name,
                    onSearch = {
                        viewModel.search(it)
                    },
                    onClearQuery = {
                        viewModel.searchClear()
                    }
                )
            },
        ) {
            val safeDrawable = with(destiny) {
                (
                    WindowInsets.safeDrawing.getTop(destiny) +
                        WindowInsets.safeDrawing.getBottom(destiny)
                    ).toDp()
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        sheetMaxHeight = with(destiny) { it.size.height.toDp() - safeDrawable }
                        if (sheetHeight == 1000.dp) sheetHeight = sheetMaxHeight
                    },
            ) {
                MainContent(
                    baseUrl = baseUrl,
                    token = token,
                    sheetSize = sheetMinHeight,
                    queueRefreshAction = queueRefreshAction.value,
                    onClearQueueRefreshAction = viewModel::clearQueueRefreshAction,
                    searchSongsAction = searchSongsAction.value,
                    onQueueRefreshAction = viewModel::onQueueRefreshAction,
                    searchAlbumsAction = searchPlaylistsAction.value,
                    searchArtistsAction = searchArtistsAction.value,
                    onClickArtist = onClickArtist,
                )
            }
        }
        if (!connectionState.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.75f)),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    baseUrl: String,
    token: String,
    sheetSize: Dp,
    queueRefreshAction: QueueRefreshAction,
    searchSongsAction: SearchSongsAction,
    searchAlbumsAction: SearchAlbumsAction,
    searchArtistsAction: SearchArtistsAction,
    onClearQueueRefreshAction: () -> Unit,
    onQueueRefreshAction: (QueueRefreshAction) -> Unit,
    modifier: Modifier = Modifier,
    onClickArtist: (artistId: String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val state = rememberPagerState(initialPage = 0) { ContentPageRoute.entries.size }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = state.currentPage,
            modifier = Modifier.fillMaxWidth(),
        ) {
            ContentPageRoute.entries.forEachIndexed { index, pageRoute ->
                Tab(
                    selected = state.currentPage == index,
                    text = {
                        Text(text = stringResource(pageRoute.titleRes))
                    },
                    onClick = {
                        scope.launch {
                            state.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        ) {
            val route = ContentPageRoute.entries[it]
            when (route) {
                ContentPageRoute.QUEUE -> {
                    QueueContent(
                        baseUrl = baseUrl,
                        token = token,
                        sheetSize = sheetSize,
                        queueRefreshAction = queueRefreshAction,
                        onClearQueueRefreshAction = onClearQueueRefreshAction,
                    )
                }

                ContentPageRoute.SONG -> {
                    SearchSongsContent(
                        baseUrl = baseUrl,
                        token = token,
                        sheetSize = sheetSize,
                        searchSongsAction = searchSongsAction,
                        onQueueRefreshAction = onQueueRefreshAction,
                    )
                }

                ContentPageRoute.ALBUM -> {
                    SearchAlbumsContent(
                        baseUrl = baseUrl,
                        token = token,
                        sheetSize = sheetSize,
                        searchAlbumsAction = searchAlbumsAction,
                        onQueueRefreshAction = onQueueRefreshAction,
                    )
                }

                ContentPageRoute.ARTIST -> {
                    SearchArtistsContent(
                        baseUrl = baseUrl,
                        token = token,
                        sheetSize = sheetSize,
                        searchArtistsAction = searchArtistsAction,
                        onClickArtist = onClickArtist,
                    )
                }
            }
        }
    }
}
