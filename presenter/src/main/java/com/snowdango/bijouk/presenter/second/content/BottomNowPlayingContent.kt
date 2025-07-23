package com.snowdango.bijouk.presenter.second.content

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
import coil3.compose.AsyncImage
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.presenter.second.component.NowPlaySongTitleComponent
import com.snowdango.bijouk.presenter.second.component.PlayBackStateButtonsComponent
import com.snowdango.bijouk.presenter.second.component.PlayPauseControllerComponent
import com.snowdango.bijouk.presenter.second.component.SeekBarComponent
import com.snowdango.bijouk.ui.image.cacheableImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNowPlayingContent(
    sheetState: SheetState,
    isEnableChange: Boolean,
    nowPlayData: NowPlayData?,
    playBackTimeData: PlayBackTimeData?,
    nowPlayingStatusData: NowPlayingStatusData,
    sheetMaxHeight: Dp,
    sheetHeight: Dp,
    imageSize: Dp,
    onPlayPause: () -> Unit,
    onSeekTo: (Float) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier,
) {

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
                            model = cacheableImageRequest(
                                context = LocalContext.current,
                                data = nowPlayData?.artwork,
                            ).build(),
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
                                    onPlayPause.invoke()
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
                            .padding(top = 60.dp)
                    )
                    PlayBackStateButtonsComponent(
                        nowPlayingStatusData = nowPlayingStatusData,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    SeekBarComponent(
                        isEnableChange = isEnableChange,
                        playBackTimeData = playBackTimeData,
                        modifier = Modifier
                            .padding(top = 20.dp),
                        onMoveSeek = {
                            onSeekTo.invoke(it)
                        }
                    )
                    PlayPauseControllerComponent(
                        playBackTimeData = playBackTimeData,
                        onClickPlayPause = {
                            onPlayPause.invoke()
                        },
                        onClickNext = {
                            onNext.invoke()
                        },
                        onClickPrevious = {
                            onPrevious.invoke()
                        },
                        modifier = Modifier
                            .padding(top = 40.dp)
                    )
                }
            }
        }
    }
}
