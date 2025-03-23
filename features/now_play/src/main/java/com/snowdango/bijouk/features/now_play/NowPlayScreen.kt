package com.snowdango.bijouk.features.now_play


import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.ui.BijouKTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import java.nio.file.WatchEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowPlayScreen(
    id: Long,
    name: String,
    baseUrl: String,
    token: String,
    viewModel: NowPlayViewModel = koinViewModel<NowPlayViewModel>{ parametersOf(id, name, baseUrl, token) }
) {

    var sheetMaxHeight by remember { mutableStateOf(1000.dp) }
    val sheetMinHeight = 140.dp
    val destiny = LocalDensity.current
    val imageMaxSize = with(destiny){ LocalView.current.width.toDp() * 3 / 5 }
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

    LaunchedEffect(sheetState.bottomSheetState.targetValue) {
        withContext(Dispatchers.Default){
            var offset = sheetState.bottomSheetState.requireOffset()
            while (sheetState.bottomSheetState.currentValue != sheetState.bottomSheetState.targetValue) {
                if(offset != sheetState.bottomSheetState.requireOffset()) {
                    offset = sheetState.bottomSheetState.requireOffset()
                    val height = with(destiny) { (screenHeight - offset).toDp() }
                    sheetHeight = if (height < sheetMaxHeight) height else sheetMaxHeight
                }
            }
            if(sheetState.bottomSheetState.targetValue == sheetState.bottomSheetState.currentValue) {
                if(sheetState.bottomSheetState.targetValue == SheetValue.PartiallyExpanded){
                    sheetHeight = sheetMinHeight
                }else {
                    sheetHeight = sheetMaxHeight
                }
            }
        }
    }

    LaunchedEffect(sheetHeight) {
        val par = (sheetHeight.value - sheetMinHeight.value) / ((sheetMaxHeight.value - sheetMinHeight.value) / 100)
        val parSize = (imageMaxSize.value - imageMinSize.value) / 100
        imageSize = (par * parSize).dp + imageMinSize
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BottomSheetScaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = sheetState,
            sheetPeekHeight = sheetMinHeight,
            sheetContent = {
                BottomSheetContent(
                    sheetState.bottomSheetState,
                    nowPlayData.value,
                    playBackTimeData.value,
                    nowPlayingStatusData.value,
                    sheetMaxHeight,
                    sheetHeight,
                    imageSize,
                    onClickPlayPause = { viewModel.playPause() },
                    onClickPrevious = { viewModel.prev() },
                    onClickNext = { viewModel.next() }
                )
            },
            topBar = {
                NowPlayTopBar(
                    name = name,
                    onSearch = {

                    },
                    onClearQuery = {

                    }
                )
            },
        ) {
            val safeDrawable = with(destiny) {
                (WindowInsets.safeDrawing.getTop(destiny) + WindowInsets.safeDrawing.getBottom(destiny)).toDp()
            }
            Box(
                modifier = Modifier.fillMaxWidth()
                    .onGloballyPositioned {
                        sheetMaxHeight = with(destiny) { it.size.height.toDp() - safeDrawable }
                        if (sheetHeight == 1000.dp) sheetHeight = sheetMaxHeight
                    },
            ) {
                SearchContent()
            }
        }
        if(!connectionState.value) {
            Box(
                modifier = Modifier.fillMaxSize()
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
fun BottomSheetContent(
    sheetState: SheetState,
    nowPlayData: NowPlayData?,
    playBackTimeData: PlayBackTimeData?,
    nowPlayingStatusData: NowPlayingStatusData,
    sheetMaxHeight: Dp,
    sheetHeight: Dp,
    imageSize: Dp,
    onClickPlayPause: () -> Unit,
    onClickNext: () -> Unit,
    onClickPrevious: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(sheetMaxHeight),
        contentAlignment = Alignment.TopCenter,
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(sheetHeight),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .height(sheetHeight),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ){
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(imageSize),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Box(
                        modifier = Modifier
                            .width(imageSize * 2)
                            .height(imageSize),
                        contentAlignment = Alignment.Center,
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(nowPlayData?.artwork)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(imageSize)
                                .clip(shape = RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                    if(sheetState.currentValue == sheetState.targetValue &&
                        sheetState.currentValue == SheetValue.PartiallyExpanded){
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text(
                                text = nowPlayData?.name ?: "",
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth()
                                    .basicMarquee()
                            )
                            Text(
                                text = nowPlayData?.artistName ?: "",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth()
                                    .alpha(0.5f)
                            )
                        }
                        Icon(
                            imageVector = if (playBackTimeData?.isPlaying == true){
                                Icons.Default.PlayArrow
                            }else{
                                Icons.Default.Pause
                            },
                            contentDescription = null,
                            modifier = Modifier.padding(start = 8.dp, end = 26.dp)
                                .size(30.dp)
                                .clickable{
                                    onClickPlayPause.invoke()
                                },
                            )
                    }else{
                        Spacer(
                            modifier = Modifier.fillMaxWidth()
                                .weight(1f)
                        )
                    }
                }
                if (
                    sheetState.currentValue == sheetState.targetValue &&
                        sheetState.currentValue == SheetValue.Expanded
                ){
                    NowPlaySongTitleComponent(
                        title = nowPlayData?.name ?: "",
                        artist = nowPlayData?.artistName ?: "",
                        modifier = Modifier
                            .padding(top = 100.dp)
                    )
                    PlayBackStateButtonsComponent(
                        nowPlayingStatusData = nowPlayingStatusData,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    SeekBarComponent(
                        playBackTimeData = playBackTimeData,
                        modifier = Modifier
                            .padding(top = 20.dp)
                    )
                    PlayPauseControllerComponent(
                        playBackTimeData = playBackTimeData,
                        onClickPlayPause = onClickPlayPause,
                        onClickNext = onClickNext,
                        onClickPrevious = onClickPrevious,
                        modifier = Modifier
                            .padding(top = 40.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowPlayTopBar(
    name: String,
    onSearch: (query: String) -> Unit,
    onClearQuery: () -> Unit,
){
    var isSearch by remember { mutableStateOf(false) }
    var inputString by remember { mutableStateOf("") }

    Crossfade(
        targetState = isSearch,
        modifier = Modifier.animateContentSize()
    ) { target ->
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
            contentAlignment = Alignment.Center,
        ) {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = inputString,
                        onQueryChange = { inputString = it },
                        onSearch = {
                            if (it.isBlank()){
                                isSearch = false
                                onClearQuery.invoke()
                            }else{
                                onSearch.invoke(inputString)
                            }
                        },
                        expanded = false,
                        onExpandedChange = { },
                        placeholder = { Text("Search") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        modifier = Modifier.onFocusChanged{
                            Log.d("Focus", it.toString())
                        }
                    )
                },
                expanded = false,
                onExpandedChange = { },
            ) {}
        }
        if(!target) {
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
                            isSearch = true
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
) {
    val tabList = listOf<String>("Queue", "Song")
    val scope = rememberCoroutineScope()
    val state  = rememberPagerState(initialPage = 2) { tabList.size }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        TabRow(
            selectedTabIndex = state.currentPage,
            modifier = Modifier.fillMaxWidth(),
        ) {
            tabList.forEachIndexed { index, title ->
                Tab(
                    selected = state.currentPage == index,
                    text = {
                        Text(text = title)
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
            modifier = modifier
                .fillMaxSize()
                .weight(1f),
        ) {
            when(it){
                0 -> {
                    Text(text = "Queue")
                }
                1 -> {
                    Text(text = "Song")
                }
            }
        }
    }
}


@Composable
fun NowPlaySongTitleComponent(
    modifier: Modifier = Modifier,
    title: String,
    artist: String,
) {
    Column (
        modifier = modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth(),
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            modifier = Modifier.padding(bottom = 4.dp)
                .fillMaxWidth()
                .basicMarquee(),
        )
        Text(
            text = artist,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
                .alpha(0.5f),
        )
    }
}


@Composable
fun PlayPauseControllerComponent(
    modifier: Modifier = Modifier,
    playBackTimeData: PlayBackTimeData?,
    onClickPlayPause: () -> Unit,
    onClickNext: () -> Unit,
    onClickPrevious: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.SkipPrevious,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
                    .clickable{
                        onClickPrevious.invoke()
                    },
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = if (playBackTimeData?.isPlaying == true){
                    Icons.Default.Pause
                }else{
                    Icons.Default.PlayArrow
                },
                contentDescription = null,
                modifier = Modifier.size(50.dp)
                    .clickable{
                        onClickPlayPause.invoke()
                    }
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.SkipNext,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
                    .clickable{
                        onClickNext.invoke()
                    },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeekBarComponent(
    modifier: Modifier = Modifier,
    playBackTimeData: PlayBackTimeData?,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth()
    ) {
        var sliderState by remember { mutableFloatStateOf(0f) }
        var isChanging by remember { mutableStateOf(false) }
        if (!isChanging) sliderState = playBackTimeData?.currentTime ?: 0f
        Slider(
            value = sliderState,
            onValueChange = {
                isChanging = true
                sliderState = it
            },
            onValueChangeFinished = {
                isChanging = false
            },
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = remember { MutableInteractionSource() },
                    thumbSize = DpSize(0.dp, 0.dp),
                )
            },
            track = {
                SliderDefaults.Track(
                    sliderState = it,
                    thumbTrackGapSize = 0.dp,
                    modifier = Modifier
                        .height(6.dp),
                    trackInsideCornerSize = 4.dp,
                    drawStopIndicator = null,
                )
            },
            valueRange = playBackTimeData?.let { 0f..playBackTimeData.duration }
                ?: 0f..Float.MAX_VALUE,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        )
        Row(
            modifier = Modifier
                .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth()
        ){
            Text(
                text = playBackTimeData?.currentTimeString ?: "00:00",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
            Text(
                text = ("-" + (playBackTimeData?.remainingTimeString ?: "00:00")),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
        }
    }
}


@Composable
fun PlayBackStateButtonsComponent(
    modifier: Modifier = Modifier,
    nowPlayingStatusData: NowPlayingStatusData
) {
    BijouKTheme {
        Row(
            modifier = modifier.padding(horizontal = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if(nowPlayingStatusData.isInLib){
                        Icons.Default.Check
                    }else{
                        Icons.Default.LibraryMusic
                    },
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                )
            }
            Box(
                modifier = Modifier.padding(start = 12.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if(nowPlayingStatusData.isFav){
                        Icons.Default.Favorite
                    }else{
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                )
            }
        }
    }
}
