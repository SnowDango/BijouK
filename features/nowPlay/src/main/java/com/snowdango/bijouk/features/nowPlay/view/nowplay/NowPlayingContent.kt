package com.snowdango.bijouk.features.nowPlay.view.nowplay

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.snowdango.bijouk.features.nowPlay.component.NowPlaySongTitleComponent
import com.snowdango.bijouk.features.nowPlay.component.PlayBackStateButtonsComponent
import com.snowdango.bijouk.features.nowPlay.component.PlayPauseControllerComponent
import com.snowdango.bijouk.features.nowPlay.component.SeekBarComponent
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowPlayingContent(
    baseUrl: String,
    token: String,
    sheetState: SheetState,
    nowPlayData: NowPlayData?,
    playBackTimeData: PlayBackTimeData?,
    nowPlayingStatusData: NowPlayingStatusData,
    sheetMaxHeight: Dp,
    sheetHeight: Dp,
    imageSize: Dp,
    modifier: Modifier = Modifier,
    viewModel: NowPlayingViewModel = koinViewModel<NowPlayingViewModel> {
        parametersOf(baseUrl, token)
    },
) {
    val isEnableChange = viewModel.isChangeableSeekFlow.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(sheetMaxHeight),
        contentAlignment = Alignment.TopCenter,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(sheetHeight),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sheetHeight),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageSize),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
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
                    if (sheetState.currentValue == sheetState.targetValue &&
                        sheetState.currentValue == SheetValue.PartiallyExpanded
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text(
                                text = nowPlayData?.name.orEmpty(),
                                maxLines = 1,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .basicMarquee()
                            )
                            Text(
                                text = nowPlayData?.artistName.orEmpty(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .alpha(0.5f)
                            )
                        }
                        Icon(
                            imageVector = if (playBackTimeData?.isPlaying == true) {
                                Icons.Default.Pause
                            } else {
                                Icons.Default.PlayArrow
                            },
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 8.dp, end = 26.dp)
                                .size(30.dp)
                                .clickable {
                                    viewModel.playPause()
                                },
                        )
                    } else {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }
                }
                if (
                    sheetState.currentValue == sheetState.targetValue &&
                    sheetState.currentValue == SheetValue.Expanded
                ) {
                    NowPlaySongTitleComponent(
                        title = nowPlayData?.name.orEmpty(),
                        artist = nowPlayData?.artistName.orEmpty(),
                        modifier = Modifier
                            .padding(top = 100.dp)
                    )
                    PlayBackStateButtonsComponent(
                        nowPlayingStatusData = nowPlayingStatusData,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    SeekBarComponent(
                        isEnableChange = isEnableChange.value,
                        playBackTimeData = playBackTimeData,
                        modifier = Modifier
                            .padding(top = 20.dp),
                        onMoveSeek = {
                            viewModel.seekTo(it)
                        }
                    )
                    PlayPauseControllerComponent(
                        playBackTimeData = playBackTimeData,
                        onClickPlayPause = {
                            viewModel.playPause()
                        },
                        onClickNext = {
                            viewModel.next()
                        },
                        onClickPrevious = {
                            viewModel.prev()
                        },
                        modifier = Modifier
                            .padding(top = 40.dp)
                    )
                }
            }
        }
    }
}
