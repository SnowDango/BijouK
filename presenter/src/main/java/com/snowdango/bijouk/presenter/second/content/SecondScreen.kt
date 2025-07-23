package com.snowdango.bijouk.presenter.second.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.ui.BijouKTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(
    connectionState: Boolean,
    nowPlayData: NowPlayData?,
    playBackTimeData: PlayBackTimeData?,
    nowPlayingStatusData: NowPlayingStatusData,
    modifier: Modifier = Modifier,
    content: @Composable (Dp) -> Unit,
) {
    val sheetMinHeight = 140.dp
    var sheetMaxHeight by remember { mutableStateOf(1000.dp) }
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

    LocalDensity.current.run {
        val windowHeight = LocalConfiguration.current.screenHeightDp.dp
        val systemBarHeight = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
        val topBarHeight = TopAppBarDefaults.TopAppBarExpandedHeight
        sheetMaxHeight = windowHeight - systemBarHeight - topBarHeight
    }

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
                    sheetState = sheetState.bottomSheetState,
                    nowPlayData = nowPlayData,
                    playBackTimeData = playBackTimeData,
                    nowPlayingStatusData = nowPlayingStatusData,
                    sheetMaxHeight = sheetMaxHeight,
                    sheetHeight = sheetHeight,
                    imageSize = imageSize,
                )
            },
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                content(sheetMinHeight)
            }
        }
        if (!connectionState) {
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

@Preview
@Composable
private fun PreviewSecondScreen() {
    BijouKTheme {
        SecondScreen(
            connectionState = true,
            nowPlayData = null,
            playBackTimeData = null,
            nowPlayingStatusData = NowPlayingStatusData(isFav = false, isInLib = false),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            )
        }
    }
}